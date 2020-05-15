package com.example.razerhackathon.db

import android.util.Log
import com.example.razerhackathon.Models.ClientInfo
import com.example.razerhackathon.global.constants
import com.example.razerhackathon.global.constants.Companion.db
import kotlinx.coroutines.tasks.await

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

        suspend fun getUser(userId : String) : ClientInfo
        {
            val docRef = db.collection("users").document(userId)
            val data = docRef.get().await()
            val userId:String = data["userId"].toString()
            val email:String = data["email"].toString()
            val firstName:String = data["firstName"].toString()
            val lastName:String = data["lastName"].toString()
            val nric:String = data["nric"].toString()

            val clientInfo = ClientInfo(userId = userId, emailAddress = email, firstName = firstName, lastName = lastName, NRIC = nric)
            return clientInfo
        }

    }
}