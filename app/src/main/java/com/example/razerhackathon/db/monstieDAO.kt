package com.example.razerhackathon.db

import android.util.Log
import com.example.razerhackathon.Models.monstie
import com.example.razerhackathon.global.constants
import com.example.razerhackathon.global.constants.Companion.db
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class monstieDAO{
    companion object{

        fun batchUpdateMonstie(username: String, status: String, monstieList : ArrayList<monstie>){
            val data = hashMapOf("availability" to status)
            for(monstie in monstieList){
                db.collection("users").document(username).collection("monsties").document(monstie.monstieId)
                    .set(data, SetOptions.merge())
            }
        }


        fun rewardMonstie(userId : String){

        }

        // The monstie will be added into the user's collection.
        fun addMonstie(userId : String, monster : monstie){
            val monstieHashMap = hashMapOf(
                "monstieId" to monster.monstieId,
                "name" to monster.name,
                "description" to monster.description,
                "imageUrl" to monster.imageUrl,
                "rarity" to monster.rarity,
                "multiplier" to monster.multiplier,
                "availability" to "Y"
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

        // The monstie will be added into the user's collection.
        fun updateMonstieStatus(userId : String, monster : monstie){
            val monstieHashMap = hashMapOf(
                "monstieId" to monster.monstieId,
                "name" to monster.name,
                "description" to monster.description,
                "imageUrl" to monster.imageUrl,
                "rarity" to monster.rarity,
                "availability" to "Y"
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


        // Get all Monsties By UserId
        suspend fun getMonstieByUserId(userId : String) : ArrayList<monstie>{
            val monstieList = arrayListOf<monstie>()
            val querySnapshot = db.collection("users").document(userId).collection("monsties").get().await()
            for (document in querySnapshot.getDocuments()) {

                val rarity : Long = document["rarity"] as Long
                val multiplier : Double = document["multiplier"] as Double

                val monster : monstie = monstie(
                    document.id,
                    document["name"].toString(),
                    document["description"].toString(),
                    rarity.toInt(),
                    document["imageUrl"].toString(),
                    multiplier,
                    document["availability"].toString()
                )

                monstieList.add(monster)
            }
            return monstieList
        }

        suspend fun getAvailableMonstieByUserId(userId : String) : ArrayList<monstie>{
            val monstieList = arrayListOf<monstie>()
            val querySnapshot = db.collection("users").document(userId).collection("monsties").whereEqualTo("availability", "Y").get().await()
//            val querySnapshot = db.collection("users").document(userId).collection("monsties").get().await()
            for (document in querySnapshot.getDocuments()) {

                val rarity : Long = document["rarity"] as Long
                val multiplier : Double = document["multiplier"] as Double
                val monster : monstie = monstie(
                    document.id,
                    document["name"].toString(),
                    document["description"].toString(),
                    rarity.toInt(),
                    document["imageUrl"].toString(),
                    multiplier,
                    document["availability"].toString()
                )

                monstieList.add(monster)
            }
            return monstieList
        }

        suspend fun getSpecificMonstieByUserId(userId : String, monstieId : String) : monstie{
            val document = db.collection("users").document(userId).collection("monsties").document(monstieId).get().await()

            val rarity : Long = document["rarity"] as Long
            val multiplier : Double = document["multiplier"] as Double
            val monster : monstie = monstie(
                document.id,
                document["name"].toString(),
                document["description"].toString(),
                rarity.toInt(),
                document["imageUrl"].toString(),
                multiplier,
                document["availability"].toString()
            )
            return monster
        }
    }
    // The monstie will be sent to the user's mailbox. Add 1 available chest to the user inventory.

}