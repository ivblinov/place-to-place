package com.example.placetoplace


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.placetoplace.data.User
import com.example.placetoplace.states.StateUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val TAG = "MyLog"

class PlaceViewModel : ViewModel() {
    var isMyPlace = false
    var isPlaceWished = false

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

    suspend fun onSave(city: String, number: String, street: String, house: String) {

        database.child(CITIES).child(city).child(number).child(MY_PLACE).child(user?.uid ?: "нет uid").setValue(true)
        database.child(USERS).child(user?.uid ?: "нет uid").child(MY_PLACE).setValue(true)

        val result = isMyPlaceAndPlaceWished()
        Log.d(TAG, "result = $result")

//        if (isMyPlaceAndPlaceWished()) Log.d(TAG, "isMyPlaceAndPlaceWished = true") else Log.d(TAG, "isMyPlaceAndPlaceWished = false")
    }

    suspend fun onSaveWish(city: String, number: String, street: String, house: String) {
        database.child(CITIES).child(city).child(number).child(PLACE_WISHED).child(user?.uid ?: "нет uid").setValue(true)
        database.child(USERS).child(user?.uid ?: "нет uid").child(PLACE_WISHED).setValue(true)

        val result = isMyPlaceAndPlaceWished()
        Log.d(TAG, "resultWished = $result")
//        if (isMyPlaceAndPlaceWished()) Log.d(TAG, "isMyPlaceAndPlaceWished = true!!!") else Log.d(TAG, "isMyPlaceAndPlaceWished = false!!!")
    }

    fun searchForMatches() {

    }

    private suspend fun isMyPlaceAndPlaceWished(): Boolean {
        val scope = CoroutineScope(Dispatchers.IO)
        val result = scope.async {
            database.child(USERS).child(user?.uid ?: "нет uid").child(MY_PLACE).get().addOnSuccessListener {
                if (it.value != null)
                    isMyPlace = it.value as Boolean
//                Log.d(TAG, "isMyPlace = $isMyPlace")
            }.addOnFailureListener { Log.d(TAG, "isPlaceWishedFail: $it") }

            database.child(USERS).child(user?.uid ?: "нет uid").child(PLACE_WISHED).get().addOnSuccessListener {
                if (it.value != null)
                    isPlaceWished = it.value as Boolean
//                Log.d(TAG, "isPlaceWished = $isPlaceWished")
            }.addOnFailureListener { Log.d(TAG, "isPlaceWishedFail: $it") }
//            Log.d(TAG, "isMyPlaceAndPlaceWished: isMyPlace = $isMyPlace and isPlaceWished = $isPlaceWished")
            return@async isMyPlace && isPlaceWished
        }
        return result.await()
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