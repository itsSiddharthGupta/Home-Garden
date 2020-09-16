package com.example.homegarden.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.homegarden.R
import com.example.homegarden.databinding.ActivityPlantDetailsBinding

class PlantDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlantDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_plant_details)
    }
}