package com.example.placetoplace.data

data class PlacesInfo(
    val myPlaceList: MutableMap<String, String>,
    val placeWishedList: MutableMap<String, String>
)