package com.example.homegarden.dataclasses

data class PlantsListResponse(
    var status: String,
    var obj: List<PlantDetails>
)