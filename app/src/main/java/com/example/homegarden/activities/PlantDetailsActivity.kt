package com.example.homegarden.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.homegarden.R
import com.example.homegarden.databinding.ActivityPlantDetailsBinding
import com.example.homegarden.viewmodels.PlantDetailsViewModel
import com.example.homegarden.viewmodels.PlantDetailsViewModelFactory

class PlantDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlantDetailsBinding
    private lateinit var viewModel: PlantDetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_plant_details)
        val plantName: String? = intent.extras?.getString("NAME")
        if (plantName != null) {
            val factory = PlantDetailsViewModelFactory(plantName)
            viewModel = ViewModelProvider(this, factory).get(PlantDetailsViewModel::class.java)
            binding.viewModel = viewModel
            binding.lifecycleOwner = this
            viewModel.isLoading.observe(this, { t ->
                if (t != null) {
                    if (t) {
                        binding.shimmerLayout.visibility = View.VISIBLE
                        binding.scrollView.visibility = View.GONE
                    } else {
                        binding.scrollView.visibility = View.VISIBLE
                        binding.shimmerLayout.visibility = View.GONE
                    }
                }
            })
        } else {
            Toast.makeText(this, "No plant", Toast.LENGTH_SHORT).show()
        }
    }
}