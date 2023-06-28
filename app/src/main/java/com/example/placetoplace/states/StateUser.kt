package com.example.placetoplace.states

sealed class StateUser {
    object IsUser : StateUser()
    object IsNotUser : StateUser()
}
