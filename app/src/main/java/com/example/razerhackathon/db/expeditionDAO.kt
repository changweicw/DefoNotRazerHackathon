package com.example.razerhackathon.db

import android.util.Log
import com.example.razerhackathon.Models.expedition
import com.example.razerhackathon.Models.monstie
import com.example.razerhackathon.global.constants
import com.example.razerhackathon.global.constants.Companion.EMPTY_EXPEDITION
import com.example.razerhackathon.global.constants.Companion.MAX_EXPEDITION
import com.example.razerhackathon.global.constants.Companion.db
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class expeditionDAO {

    companion object{
        suspend fun getAllExpeditions() : ArrayList<expedition>{
            val expeditionList = arrayListOf<expedition>()
            val querySnapshot = constants.db.collection("expeditions").get().await()
            for (document in querySnapshot.getDocuments()) {
                expeditionList.add(document.toObject(expedition::class.java) as expedition)
            }
            return expeditionList
        }

        // Setting Expedition
        suspend fun getUserExpedition(documentId : String, userId : String) : expedition{
            val documentSnapshot = db.collection("users").document(userId).collection("expeditions").document(documentId).get().await()
            val expeditionObj = documentSnapshot.toObject(expedition::class.java) as expedition
            expeditionObj.expeditionId = documentId
            return expeditionObj
        }


        suspend fun getAllOngoingExpeditions(username : String) : ArrayList<expedition>{
            val expeditionList = arrayListOf<expedition>()
            val querySnapshot = constants.db.collection("users").document(username).collection("expeditions").get().await()
            Log.d("EXP_NUM", "I have ${querySnapshot.getDocuments()} available expedition")
            for (document in querySnapshot.getDocuments()) {
                if (document["expeditionId"] != EMPTY_EXPEDITION.expeditionId){
                    val expeditionObj = document.toObject(expedition::class.java) as expedition
                    expeditionObj.expeditionId = document.id
                    expeditionList.add(expeditionObj)
                }

            }
            return expeditionList
        }

        fun createEmptyExpedition(userId : String){
            val expeditionHashMap = hashMapOf(
                "expeditionId" to EMPTY_EXPEDITION.expeditionId,
                "name" to EMPTY_EXPEDITION.name,
                "description" to "",
                "imageUrl" to "",
                "minRewards" to 0,
                "maxRewards" to 0,
                "multiplier" to 0,
                "chances" to 0,
                "timeTaken" to 0
            )

            for(i in 1.. MAX_EXPEDITION){
                db.collection("users").document(userId).collection("expeditions").add(expeditionHashMap)
                    .addOnSuccessListener { documentReference ->
                        Log.d(constants.logTestDAO, "DocumentSnapshot added with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w(constants.logTestDAO, "Error adding document", e)
                    }
            }
        }


        fun setExpeditionToEmpty(documentId: String, userId : String){
            val expeditionHashMap = hashMapOf(
                "expeditionId" to EMPTY_EXPEDITION.expeditionId,
                "name" to EMPTY_EXPEDITION.name,
                "description" to "",
                "imageUrl" to "",
                "minRewards" to 0,
                "maxRewards" to 0,
                "multiplier" to 0,
                "chances" to 0,
                "timeTaken" to 0
            )

                db.collection("users").document(userId).collection("expeditions")
                    .document(documentId).set(expeditionHashMap, SetOptions.merge())

        }

        suspend fun getExpedition(expeditionId : String) : expedition{
            val documentSnapshot = constants.db.collection("expeditions").document(expeditionId).get().await()
            return documentSnapshot.toObject(expedition::class.java) as expedition
        }

        // Get Available Epedition
        suspend fun getAvailableExpeditionId(userId : String) : String{
            val querySnapshot = constants.db.collection("users")
                .document(userId)
                .collection("expeditions")
                .whereEqualTo("expeditionId", EMPTY_EXPEDITION.expeditionId).get().await()
            return querySnapshot.documents[0].id
        }

        // Setting Expedition
        fun setExpedition(documentId : String, userId : String, expedition : expedition){
            db.collection("users").document(userId).collection("expeditions").document(documentId).set(expedition)
                .addOnSuccessListener { documentReference ->
                    Log.d(constants.logTestDAO, "DocumentSnapshot added with ID: $documentId")
                }
                .addOnFailureListener { e ->
                    Log.w(constants.logTestDAO, "Error adding document", e)
                }
        }

        fun setLoadout(documentId : String, userId : String, monstieList : ArrayList<monstie>){

            for(monstie in monstieList){
                db.collection("users").document(userId).collection("expeditions").document(documentId).collection("loadout").add(monstie)
                    .addOnSuccessListener { documentReference ->
                        Log.d(constants.logTestDAO, "DocumentSnapshot added with ID: $documentId")
                    }
                    .addOnFailureListener { e ->
                        Log.w(constants.logTestDAO, "Error adding document", e)
                    }
            }

        }

        suspend fun getLoadout(documentId : String, userId : String) : ArrayList<monstie>{
            val monstieList = arrayListOf<monstie>()
            val querySnapshot = db.collection("users").document(userId).collection("expeditions").document(documentId).collection("loadout").get().await()

            for (document in querySnapshot.getDocuments()) {
                if (document["expeditionId"] != EMPTY_EXPEDITION.expeditionId){
                    val monstieObjects = document.toObject(monstie::class.java) as monstie
                    monstieList.add(monstieObjects)
                }
            }
            return monstieList
        }

        suspend fun isExpeditionAvailable(userId : String) : Boolean{
            val querySnapshot = constants.db.collection("users")
                .document(userId)
                .collection("expeditions")
                .whereEqualTo("expeditionId", EMPTY_EXPEDITION.expeditionId).get().await()
            Log.d("Query SnapshotSize", querySnapshot.size().toString())
            if(MAX_EXPEDITION - querySnapshot.size() >= MAX_EXPEDITION) return false
            return true
        }
    }
}