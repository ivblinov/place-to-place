package com.example.placetoplace.data


class User(city: String, number: String) {
    val name: String? = null
    val myKindergarten: Kindergarten = Kindergarten(city, number)
}