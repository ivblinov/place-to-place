package com.example.placetoplace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.placetoplace.databinding.ActivityMainBinding
import com.example.placetoplace.ui.menu.ChatsScreen
import com.example.placetoplace.ui.menu.FilterScreen
import com.example.placetoplace.ui.menu.MapScreen
import com.example.placetoplace.ui.menu.Screen3Main
import com.google.firebase.database.DatabaseReference

private const val TAG = "MyLog"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idBottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.add_my_ad -> switching(Screen3Main())
                R.id.filter -> switching(FilterScreen())
                R.id.map -> switching(MapScreen())
                R.id.chats -> switching(ChatsScreen())
            }
            true
        }



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

    private fun switching(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.nav_host, fragment)
        }
    }
}

//"https://placetoplaceproject-default-rtdb.europe-west1.firebasedatabase.app"