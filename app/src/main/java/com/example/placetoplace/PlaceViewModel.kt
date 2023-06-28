package com.example.placetoplace


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.placetoplace.states.StateUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "MyLog"

class PlaceViewModel : ViewModel() {

    private val _stateUser = MutableStateFlow<StateUser>(StateUser.IsNotUser)
    val stateUser = _stateUser.asStateFlow()

    private var auth = Firebase.auth

    fun checkUser() {
        viewModelScope.launch {
            val user = auth.currentUser
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
}