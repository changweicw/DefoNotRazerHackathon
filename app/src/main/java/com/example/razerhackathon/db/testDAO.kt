package com.example.razerhackathon.db

import android.util.Log
import com.example.razerhackathon.CONSTANTS
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class testDAO {
    // Create static methods to access the database
    companion object{
    val db = Firebase.firestore
        
        fun addUserDate(firstName : String, lastName : String, born : String){
            val user = hashMapOf(
                "firstName" to firstName,
                "lastName" to lastName,
                "born" to born
            )
            
            // Add a new document with a generated ID
            db.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Log.d(CONSTANTS.logTestDAO, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(CONSTANTS.logTestDAO, "Error adding document", e)
                }
        }


    }
}