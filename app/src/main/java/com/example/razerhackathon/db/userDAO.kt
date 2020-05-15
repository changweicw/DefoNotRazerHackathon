package com.example.razerhackathon.db

import android.util.Log
import com.example.razerhackathon.global.constants
import com.example.razerhackathon.global.constants.Companion.db

class userDAO {

    companion object{

        // Stores a record inside Firebase under the collection name "user"
        fun createUser(userId : String, email : String, firstName : String, lastName : String, nric : String, nricExpiry : String){
            val user = hashMapOf(
                "userId" to userId,
                "email" to email,
                "firstName" to firstName,
                "lastName" to lastName,
                "nric" to nric,
                "nricExpiry" to nricExpiry
            )

            // Add a new document with a generated ID
            db.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Log.d(constants.logTestDAO, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(constants.logTestDAO, "Error adding document", e)
                }
        }
    }
}