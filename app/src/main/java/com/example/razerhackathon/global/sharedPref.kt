package com.example.razerhackathon.global

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.example.razerhackathon.global.constants.Companion.PREF_NAME


class sharedPref(val ctx : Activity) {
    private val sharedPreference : SharedPreferences = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor:SharedPreferences.Editor =  sharedPreference.edit()

    fun putValue(key: String, value : String){
        editor.putString(key, value)
    }

    fun putValue(key: String, value : Int){
        editor.putInt(key, value)
    }

    fun putValue(key : String, value : Long){
        editor.putLong(key, value)
    }

    fun removeValue(key : String){
        editor.remove(key)
    }

    fun commit(){
        editor.commit()
    }


}