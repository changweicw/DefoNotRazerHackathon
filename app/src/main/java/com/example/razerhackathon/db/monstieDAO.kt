package com.example.razerhackathon.db

import android.util.Log
import com.example.razerhackathon.Models.monstie
import com.example.razerhackathon.global.constants
import com.example.razerhackathon.global.constants.Companion.db
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class monstieDAO{
    companion object{
        fun rewardMonstie(userId : String){

        }

        // The monstie will be added into the user's collection.
        fun addMonstie(userId : String, monster : monstie){
            val monstieHashMap = hashMapOf(
                "name" to monster.name,
                "description" to monster.description,
                "imageUrl" to monster.imageUrl,
                "rarity" to monster.rarity
            )

            // Add a new document with a generated ID
            db.collection("users").document(userId).collection("monsties").add(monstieHashMap)
                .addOnSuccessListener { documentReference ->
                    Log.d(constants.logTestDAO, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(constants.logTestDAO, "Error adding document", e)
                }
        }

        // Monstie will be removed from the user's collection.
        fun removeMonstie(){

        }

        // Get Monstie By Rarity
        suspend fun getMonstieByRarity(rarity : Int) : MutableList<monstie>{
            val monstieList = mutableListOf<monstie>()
            val querySnapshot = db.collection("monsties").whereEqualTo("rarity", rarity).get().await()
            for (document in querySnapshot.getDocuments()) {
                monstieList.add(document.toObject(monstie::class.java) as monstie)
            }
            return monstieList
        }
    }
    // The monstie will be sent to the user's mailbox. Add 1 available chest to the user inventory.

}