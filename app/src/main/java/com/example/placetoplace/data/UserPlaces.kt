package com.example.placetoplace.data

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserPlaces(
    var myPlace: Boolean = false,
    var placeWished: Boolean = false
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "myPlace" to myPlace,
            "placeWished" to placeWished
        )
    }
}
