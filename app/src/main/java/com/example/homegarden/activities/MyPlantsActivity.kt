package com.example.homegarden.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.homegarden.R
import com.example.homegarden.adapters.PlantsInfoListAdapter
import com.example.homegarden.databinding.ActivityMyPlantsBinding
import com.example.homegarden.dataclasses.PlantBasicInfo

class MyPlantsActivity : AppCompatActivity(), PlantsInfoListAdapter.OnPlantItemListener {
    private lateinit var binding: ActivityMyPlantsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_plants)
        binding.recyclerViewPlantsInfo.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerViewPlantsInfo.adapter = PlantsInfoListAdapter(
            this,
            getStaticPlantsBasicInfo(),
            this
        )
    }

    private fun getStaticPlantsBasicInfo(): List<PlantBasicInfo> {
        return listOf(
            PlantBasicInfo(
                1,
                "Peace Lily",
                "Peace Lilly is an indoor plant that has become universally aligned with these aspirations.",
                R.drawable.peace_lily
            ),
            PlantBasicInfo(
                2,
                "Aloe Vera",
                "Aloe Vera is also called the “Miracle plant”. There are good reasons for this. It’s renowned worldwide for having medicinal properties  that  rejuvenate, soothe and heal the human body.",
                R.drawable.aloe_vera
            ),
            PlantBasicInfo(
                3,
                "Ferns",
                "Ferns are counted among one of the oldest species of plants on earth, dating back to prehistoric times. Ferns belong to a group of vascular seeds, that bear neither flowers nor seeds.",
                R.drawable.nephrolepis_exaltata_aurea_pivla_fern
            ),
            PlantBasicInfo(
                4,
                "Green Spider Plant",
                "Spider plants have leaves that look like blades of grass with streaks of different colors either in the center or at the edges.",
                R.drawable.spider_plant
            ),
            PlantBasicInfo(
                5,
                "Indian Basil",
                "It’s been grown in India from as far back as 5,000 years. No wonder then, that Indians harbour a deep cultural and religious attachment with the plant from time immemorial.",
                R.drawable.holy_basil
            )
        )
    }

    override fun onPlantItemClick(plant: PlantBasicInfo) {
        Toast.makeText(this, plant.toString(), Toast.LENGTH_SHORT).show()
    }
}