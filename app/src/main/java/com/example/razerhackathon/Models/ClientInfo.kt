package com.example.razerhackathon.Models

import android.app.Activity
import com.example.razerhackathon.global.constants
import com.example.razerhackathon.global.sharedPref


class ClientInfo(
    var firstName: String = "",
    var lastName: String = "",
    var NRIC: String = "",
    var NRIC_Issued: String = "",
    var emailAddress: String = "",
    var password: String = "",
    var confirmPassword: String = "",
    var userId : String = ""
){
    fun saveSharedPreference(ctx : Activity){

        // Saving it into shared preferences
        val sharedPref = sharedPref(ctx)
        sharedPref.putValue(constants.USERNAME, userId)
        sharedPref.putValue(constants.LAST_NAME, lastName)
        sharedPref.putValue(constants.FIRST_NAME, firstName)
        sharedPref.putValue(constants.NRIC, NRIC)
        sharedPref.putValue(constants.EMAIL, emailAddress)
        sharedPref.commit()
    }
}