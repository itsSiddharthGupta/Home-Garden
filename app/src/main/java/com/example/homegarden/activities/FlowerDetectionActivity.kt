package com.example.homegarden.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.homegarden.R
import com.example.homegarden.databinding.ActivityFlowerDetectionBinding
import com.example.homegarden.util.ImageClassifier
import com.example.homegarden.viewmodels.FlowerDetectionViewModel
import com.example.homegarden.viewmodels.FlowerDetectionViewModelFactory
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.FileNotFoundException


class FlowerDetectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFlowerDetectionBinding
    private lateinit var viewModel: FlowerDetectionViewModel
    private val CHOOSE_IMAGE = 1001
    private lateinit var photoImage: Bitmap
    private lateinit var classifier: ImageClassifier
    private lateinit var mCropImageUri: Uri
    private val INPUT_SIZE = 224

    /** Input image size of the model along x axis.  */
    private var imageSizeX = 0

    /** Input image size of the model along y axis.  */
    private var imageSizeY = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_flower_detection)
        classifier = ImageClassifier.create(this, ImageClassifier.Device.CPU, 1)!!
        val factory = FlowerDetectionViewModelFactory(classifier)
        viewModel = ViewModelProvider(this, factory).get(FlowerDetectionViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        // Updates the input image size.
        imageSizeX = classifier.getImageSizeX();
        imageSizeY = classifier.getImageSizeY();
        binding.fabAddPic.setOnClickListener {
            chooseImagePicker()
        }
        binding.btnDetails.setOnClickListener {
            startActivity(
                Intent(
                    this@FlowerDetectionActivity,
                    PlantDetailsActivity::class.java
                ).putExtra("NAME", viewModel.flowerDetected.value)
            )
        }
        viewModel.flowerDetected.observe(this, { t ->
            if (t != null) {
                binding.textResult.text = t
                binding.btnDetails.visibility = View.VISIBLE
            } else {
                binding.btnDetails.visibility = View.GONE
            }
        })
        viewModel.isModelRunning.observe(this, { t ->
            if (t != null) {
                Log.d("isModelRunning", "$t")
                binding.btnDetect.isEnabled = !t
            }
        })
    }

    private fun chooseImagePicker() {
        CropImage.startPickImageActivity(this)
    }

    private fun startCropImageActivity(image: Uri) {
        CropImage.activity(image)
            .setAspectRatio(1, 1)
            .setGuidelines(CropImageView.Guidelines.ON)
            .start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val result = CropImage.getActivityResult(data)
            photoImage = MediaStore.Images.Media.getBitmap(this.contentResolver, result.uri)
            photoImage = Bitmap.createScaledBitmap(photoImage, INPUT_SIZE, INPUT_SIZE, false)
            viewModel.imageSelected.value = photoImage
        } else if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            val imageUri: Uri = CropImage.getPickImageResultUri(this, data)

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri
                requestPermission()
            } else {
                // no permissions required or already granted, can start crop image activity
                startCropImageActivity(imageUri)
            }
        } else if (requestCode == CHOOSE_IMAGE && resultCode == Activity.RESULT_OK)
            try {
                val stream = data?.data?.let { contentResolver!!.openInputStream(it) }
                if (::photoImage.isInitialized) photoImage.recycle()
                photoImage = BitmapFactory.decodeStream(stream)
                photoImage =
                    Bitmap.createScaledBitmap(photoImage, INPUT_SIZE, INPUT_SIZE, false)
                viewModel.imageSelected.value = photoImage
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
    }

    private fun requestPermission() {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat
                .requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE
                );
        } else {
            startCropImageActivity(mCropImageUri)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // required permissions granted, start crop image activity
                startCropImageActivity(mCropImageUri)
            } else {
                Toast.makeText(
                    this,
                    "Cancelling, required permissions are not granted",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        classifier.close()
    }
}