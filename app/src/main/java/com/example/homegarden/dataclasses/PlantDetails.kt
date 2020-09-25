package com.example.homegarden.dataclasses
data class PlantDetails(
    val id: Int,
    val name: String,
    val temperature: String,
    val imageUrl: String,
    val isIndoor: Boolean,
    val water: String,
    val description: String,
    val careInfo: PlantCareDetails
) {
    data class PlantCareDetails(
        val id: Int,
        val waterDetails: String,
        val soilDetails: String,
        val lightDetails: String,
        val temperatureDetails: String
    )
}