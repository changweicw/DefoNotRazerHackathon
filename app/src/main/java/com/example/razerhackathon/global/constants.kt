package com.example.razerhackathon.global

import com.example.razerhackathon.Models.expedition
import com.example.razerhackathon.Models.monstie
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class constants {
    companion object{
        val logTestDAO = "TestDAO_LOG"
        val logSignIn = "SignIn_LOG"

        // Database constant variables
        val db = Firebase.firestore

        // User Variables
        val documentId = "documentId"
        val NRIC = "nric"
        val NRIC_EXP = "nricExp"
        val FIRST_NAME = "firstName"
        val LAST_NAME = "lastName"
        val USERNAME = "username"
        val EMAIL = "email"

        // Shared Preference Loadout Slots // monstie ID
        val FIRST_SLOT = "firstSlot"
        val SECOND_SLOT = "secondSlot"
        val THIRD_SLOT = "thirdSlot"
        val FOURTH_SLOT = "fourthSlot"
        val SLOT_NUMBER = "slotNumber"

        // Shared Preference
        val PRIVATE_MODE = 0
        val PREF_NAME = "razor-hackathon"


        val MAMBU_ACCESS = "VGVhbTg2OnBhc3NDQzdERjNBM0I="
        val ASSIGNED_BRANCH_KEY = "8a8e878e71c7a4d70171ca696b9112ef"

        val MAMBUID = "mambuId"
        val SAVINGSACCOUNTID = "savingsAccountId"

        // Monstie Rarity
        val COMMON = 1
        val RARE = 2
        val EPIC = 3
        val LEGENDARY = 4

        // Monstie Status
        val AVAILABLE : String = "Y"
        val NOT_AVAILABLE : String = "N"

        // Empty Expedition
        val EMPTY_EXPEDITION = expedition(
            "emptyExpedition",
            "Empty Expedition"
        )

        // Empty Monstie
        val EMPTY_MONSTIE = monstie(
            monstieId = "emptyMonstie",
            imageUrl = "no_monster"
        )

        val MAX_EXPEDITION = 3


        // CHEAT FIELDS
        val SHORTCUT_EMAIL = "fifi@g.com"
        val SHORTCUT_PASSWORD = "123456"
    }
}