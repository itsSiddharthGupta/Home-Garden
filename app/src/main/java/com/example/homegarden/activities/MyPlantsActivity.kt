package com.example.homegarden.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.homegarden.R
import com.example.homegarden.adapters.PlantsInfoListAdapter
import com.example.homegarden.databinding.ActivityMyPlantsBinding
import com.example.homegarden.dataclasses.PlantBasicInfo
import com.example.homegarden.dataclasses.PlantDetails
import com.example.homegarden.viewmodels.MyPlantsActivityViewModel

class MyPlantsActivity : AppCompatActivity(), PlantsInfoListAdapter.OnPlantItemListener {
    private lateinit var binding: ActivityMyPlantsBinding
    private lateinit var viewModel: MyPlantsActivityViewModel
    private lateinit var adapter: PlantsInfoListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_plants)
        viewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                MyPlantsActivityViewModel::class.java
            )
        adapter = PlantsInfoListAdapter(this)
        val isIndoor = intent.extras?.getBoolean("IS-INDOOR")
        if (isIndoor != null && isIndoor) {
            viewModel.getIndoorPlants()
            binding.txtTitle.text = "My Room Plants"
            binding.txtTips.text = "Interior plants need less water in winter. A major cause of killing any kind of plant is over-watering."
        } else {
            viewModel.getOutdoorPlants()
            binding.txtTitle.text = "My Outdoor Plants"
            binding.txtTips.text = "Outdoor plants need more water and sunlight. A major cause of killing outdoor plants is lack of sunlight."
        }
        viewModel.plants.observe(this, {
            if (it != null) {
                adapter.fillData(it)
            }
        })
        binding.recyclerViewPlantsInfo.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerViewPlantsInfo.adapter = adapter
    }

    override fun onPlantItemClick(plant: PlantDetails) {
        startActivity(Intent(this, PlantDetailsActivity::class.java).putExtra("NAME", plant.name))
    }
}