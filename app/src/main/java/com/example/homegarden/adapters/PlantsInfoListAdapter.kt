package com.example.homegarden.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.marginEnd
import androidx.recyclerview.widget.RecyclerView
import com.example.homegarden.R
import com.example.homegarden.dataclasses.PlantBasicInfo
import com.example.homegarden.util.UtilityMethods


class PlantsInfoListAdapter(
    private val context: Context,
    private val plantsInfoList: List<PlantBasicInfo>,
    private val listener: OnPlantItemListener
) :
    RecyclerView.Adapter<PlantsInfoListAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View, listener: OnPlantItemListener) :
        RecyclerView.ViewHolder(view) {
        var imgPlant: ImageView = view.findViewById(R.id.imgPlant)
        var txtPlantName: TextView = view.findViewById(R.id.txtPlantName)

        init {
            view.setOnClickListener { listener.onPlantItemClick(plantsInfoList[adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = View.inflate(context, R.layout.layout_plant_item, null)
        val params = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.setMargins(8,16,8,16)
        view.layoutParams = params
        return MyViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val plantInfo: PlantBasicInfo = plantsInfoList[position]
        holder.txtPlantName.text = plantInfo.name
//        holder.txtPlantDescription.text = plantInfo.description
        holder.imgPlant.setImageDrawable(context.resources.getDrawable(plantInfo.imageId))
    }

    override fun getItemCount() = plantsInfoList.size

    interface OnPlantItemListener {
        fun onPlantItemClick(plant: PlantBasicInfo)
    }
}

