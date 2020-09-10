package com.example.homegarden.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import com.example.homegarden.R
import com.example.homegarden.util.ImageClassifier
import com.example.homegarden.util.Keys.INPUT_SIZE
import kotlinx.android.synthetic.main.activity_image.*
import java.io.FileNotFoundException


class ImageActivity : AppCompatActivity() {
    private val CHOOSE_IMAGE = 1001
    private lateinit var photoImage: Bitmap
    private lateinit var classifier: ImageClassifier

    /** Input image size of the model along x axis.  */
    private var imageSizeX = 0

    /** Input image size of the model along y axis.  */
    private var imageSizeY = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        classifier = ImageClassifier.create(this, ImageClassifier.Device.CPU, 1)!!
        // Updates the input image size.
        imageSizeX = classifier.getImageSizeX();
        imageSizeY = classifier.getImageSizeY();
        imageResult.setOnClickListener {
            choosePicture()
        }
    }

    private fun choosePicture() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, CHOOSE_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CHOOSE_IMAGE && resultCode == Activity.RESULT_OK)
            try {
                val stream = data?.data?.let { contentResolver!!.openInputStream(it) }
                if (::photoImage.isInitialized) photoImage.recycle()
                photoImage = BitmapFactory.decodeStream(stream)
                photoImage = Bitmap.createScaledBitmap(photoImage, INPUT_SIZE, INPUT_SIZE, false)
                imageResult.setImageBitmap(photoImage)
                val startTime = SystemClock.uptimeMillis()
                val results: List<ImageClassifier.Recognition?>? =
                    classifier.recognizeImage(photoImage, 0)
                val lastProcessingTimeMs = SystemClock.uptimeMillis() - startTime

                txtResult.text = results.toString() + " , Time : " + lastProcessingTimeMs + ", x,y : " + imageSizeX + "," + imageSizeY

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        classifier.close()
    }
}