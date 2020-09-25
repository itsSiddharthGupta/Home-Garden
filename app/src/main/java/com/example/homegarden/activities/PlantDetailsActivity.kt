package com.example.homegarden.activities

import android.os.Bundle
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
        val factory = PlantDetailsViewModelFactory("Spider Plant")
        viewModel = ViewModelProvider(this, factory).get(PlantDetailsViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}