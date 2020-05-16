package com.example.razerhackathon.db

import android.util.Log
import com.example.razerhackathon.Models.ClientInfo
import com.example.razerhackathon.global.constants
import com.example.razerhackathon.global.constants.Companion.db
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class userDAO {

    companion object{

        // Stores a record inside Firebase under the collection name "user"
        fun createUser(clientInfo: ClientInfo){
            val user = hashMapOf(
                "userId" to clientInfo.userId,
                "email" to clientInfo.emailAddress,
                "firstName" to clientInfo.firstName,
                "lastName" to clientInfo.lastName,
                "nric" to clientInfo.NRIC,
                "nricExpiry" to clientInfo.NRIC_Issued,
                "coins" to 0
            )

            // Add a new document with a generated ID
            db.collection("users").document(clientInfo.userId)
                .set(user)
                .addOnSuccessListener { documentReference ->
                    Log.d(constants.logTestDAO, "DocumentSnapshot added with ID: $clientInfo.userId")
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

        suspend fun getCoins(userId : String) : Int
        {
            val docRef = db.collection("users").document(userId)
            val data = docRef.get().await()
            val coins : Long = data["coins"] as Long
            return coins.toInt()
        }

        fun setCoins(userId : String, value : Int)
        {
            val data = hashMapOf("coins" to value)
            db.collection("users").document(userId)
                .set(data, SetOptions.merge())

        }

    }
}