package com.example.placetoplace


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.placetoplace.data.User
import com.example.placetoplace.data.UserPlaces
import com.example.placetoplace.states.StateUser
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val TAG = "MyLog"

class PlaceViewModel : ViewModel() {

    init {
        Log.d(TAG, "init: ")
    }

    private val _stateUser = MutableStateFlow<StateUser>(StateUser.IsNotUser)
    val stateUser = _stateUser.asStateFlow()

    private var auth = Firebase.auth
    private val user = auth.currentUser

    private val database: DatabaseReference = Firebase.database.reference

    fun checkUser() {
        viewModelScope.launch {
//            val user = auth.currentUser
            if (user != null) {
                _stateUser.value = StateUser.IsUser
//            reload()
                Log.d(TAG, "User is in system: $user")
            }
        }
    }

    fun onClick(email: String, password: String) {
        Log.d(TAG, "onClick: OK")
        createAccount(email, password)
    }

    fun onSave(city: String, number: String, street: String, house: String) {
        database.child(CITIES).child(city).child(number).child(MY_PLACE).child(user?.uid ?: "нет uid").setValue(true)
        database.child(USERS).child(user?.uid ?: "нет uid").child(MY_PLACE).setValue(true)
    }

    fun onSaveWish(city: String, number: String, street: String, house: String) {
        database.child(CITIES).child(city).child(number).child(PLACE_WISHED).child(user?.uid ?: "нет uid").setValue(true)
        database.child(USERS).child(user?.uid ?: "нет uid").child(PLACE_WISHED).setValue(true)
    }

    fun searchForMatches() {

    }

    private val userReference = database.child(USERS).child(user?.uid ?: "нет uid")
    var incrementUser = 0

    fun isMyPlaceAndPlaceWished() {
        val userListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userPlaces = snapshot.getValue<UserPlaces>()
                Log.d(TAG, "changes - $userPlaces")
                Log.d(TAG, "incrementUser = ${incrementUser++}")
                if (userPlaces != null) {
                    Log.d(TAG, "myPlace = ${userPlaces.myPlace}")
                    Log.d(TAG, "placeWished = ${userPlaces.placeWished}")
                }
                Log.d(TAG, "")
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: $error")
            }
        }
        userReference.addValueEventListener(userListener)
    }

//    myRef.setValue("Hi")

/*    database.child("users").child("username").setValue("Maria")
    database.child("users").child("userlastname").push().setValue("Ivanova")
    database.child("users").child("userage").setValue(21)*/



/*    private fun validateForm(email: String, password: String): Boolean {
        var valid = true


        if (TextUtils.isEmpty(email)) {
            binding.email.error = "Required."
            valid = false
        } else {
            binding.email.error = null
        }

        if (TextUtils.isEmpty(password)) {
            binding.password.error = "Required."
            valid = false
        } else {
            binding.password.error = null
        }

        return valid
    }*/

    private fun createAccount(email: String, password: String) {
        Log.d(TAG, "createAccount:$email")
/*        if (!validateForm(email, password)) {
            return
        }*/

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    Log.d(TAG, "createAccount: $user")

//                    updateUI(user)
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)

/*                    Toast.makeText(
                        this,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()*/

//                    updateUI(null)
                }
            }
    }



    override fun onCleared() {
        Log.d(TAG, "onCleared: ")
        super.onCleared()

    }

    companion object {
        const val CITIES = "cities"
        const val MY_PLACE = "my_place"
        const val PLACE_WISHED = "place_wished"
        const val USERS = "users"
    }
}