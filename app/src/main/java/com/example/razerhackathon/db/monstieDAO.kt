package com.example.razerhackathon.db

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class monstieDAO{
    val db = Firebase.firestore

    // The monstie will be sent to the user's mailbox. Add 1 available chest to the user inventory.
    fun rewardMonstie(userId : String){

    }

    // The monstie will be added into the user's collection.
    fun addMonstie(){

    }

    // Monstie will be removed from the user's collection.
    fun removeMonstie(){

    }

}