package com.example.homegarden.dataclasses

data class PlantBasicInfo(val id:Int, val name: String, val description: String){
    var imageId: Int = 0

    constructor(id:Int, name: String, description: String, image: Int) : this(id, name, description) {
        imageId = image
    }
}