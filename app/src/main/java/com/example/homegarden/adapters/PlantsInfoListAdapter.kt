package com.example.homegarden.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homegarden.R
import com.example.homegarden.dataclasses.PlantBasicInfo
import com.example.homegarden.dataclasses.PlantDetails
import java.util.*
import kotlin.collections.ArrayList


class PlantsInfoListAdapter(
    private val listener: OnPlantItemListener
) : RecyclerView.Adapter<PlantsInfoListAdapter.MyViewHolder>() {

    private var plantsList: List<PlantDetails> = ArrayList()

    inner class MyViewHolder(view: View, listener: OnPlantItemListener) :
        RecyclerView.ViewHolder(view) {
        private var imgPlant: ImageView = view.findViewById(R.id.imgPlant)
        private var txtPlantName: TextView = view.findViewById(R.id.txtPlantName)
        private var txtTemperatureInfo: TextView = view.findViewById(R.id.txtTemperatureInfo)
        private var txtWaterInfo: TextView = view.findViewById(R.id.txtWaterInfo)

        init {
            view.setOnClickListener { listener.onPlantItemClick(plantsList[adapterPosition]) }
        }

        fun bind(plant: PlantDetails){
            Glide.with(itemView).load(plant.imageUrl).into(imgPlant)
            txtPlantName.text = plant.name.capitalize(Locale.ROOT)
            txtTemperatureInfo.text = plant.temperature
            txtWaterInfo.text = plant.water
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = View.inflate(parent.context, R.layout.layout_plant_item, null)
        val params = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(8, 16, 8, 16)
        view.layoutParams = params
        return MyViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val plant = plantsList[position]
        holder.bind(plant)
    }

    override fun getItemCount() = plantsList.size

    fun fillData(plantsList: List<PlantDetails>){
        this.plantsList = plantsList
        notifyDataSetChanged()
    }
    interface OnPlantItemListener {
        fun onPlantItemClick(plant: PlantDetails)
    }
}

