package com.example.placetoplace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

private const val TAG = "MyLog"

class MainActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Write a message to the database
/*        val database = Firebase.database
        val myRef = database.getReference("message")

        myRef.setValue("Hi")*/


/*        database = Firebase.database.reference
        database.child("users").child("username").setValue("Maria")
        database.child("users").child("userlastname").push().setValue("Ivanova")
        database.child("users").child("userage").setValue(21)*/









        // Read from the database
/*        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<String>()
                Log.d(TAG, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })*/

/*        myRef.setValue("How much?")
        myRef.setValue("Hi")*/




/*        val childUpdates = hashMapOf<String, Any>(
            "/users/username" to "Ivan"
        )

        database.updateChildren(childUpdates)*/





    }
}

//"https://placetoplaceproject-default-rtdb.europe-west1.firebasedatabase.app"