package com.example.razerhackathon.Models

import android.app.Activity
import com.example.razerhackathon.db.userDAO
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
    var userId : String = "",
    var mambuId : String = "",
    var savingsAccountId : String = ""
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




    fun createClient(){
        userDAO.createUser(this)
    }

    companion object{
        fun clearShared(ctx : Activity){

            // Saving it into shared preferences
            val sharedPref = sharedPref(ctx)
            sharedPref.putValue(constants.USERNAME, "")
            sharedPref.putValue(constants.LAST_NAME, "")
            sharedPref.putValue(constants.FIRST_NAME, "")
            sharedPref.putValue(constants.NRIC, "")
            sharedPref.putValue(constants.EMAIL, "")
            sharedPref.commit()
        }
    }

}