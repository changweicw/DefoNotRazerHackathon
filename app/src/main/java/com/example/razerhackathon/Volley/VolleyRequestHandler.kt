package com.example.razerhackathon.Volley

import android.util.Log
import android.view.View
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.example.razerhackathon.Models.ClientInfo
import com.example.razerhackathon.global.constants.Companion.MAMBU_ACCESS

object VolleyRequestHandler {
    fun getUserProfile(view: View, mambuId: String) {
        // Instantiate the RequestQueue.
        val queue = VolleySingleton.getInstance(view.context).requestQueue
        val url = "https://razerhackathon.sandbox.mambu.com/api/clients/${mambuId}"
        // Request a string response from the provided URL.
        val stringRequest = object: StringRequest(
            Method.GET, url,
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
//                 Log.d("HTTP Results", response.substring(0, 500))
                Log.d("HTTP Results", response.toString())
            },
            Response.ErrorListener {
                //                    textView.text = "That didn't work!"
                Log.d("HTTP Results", it.toString())
            })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Basic ${MAMBU_ACCESS}"
                headers["Content-Type"] = "application/json"
                return headers
            }
        }
        // Add the request to the RequestQueue.
        VolleySingleton.getInstance(view.context).addToRequestQueue(stringRequest)

    }

    fun createNewProfile(view: View, newClient: ClientInfo) {

    }

}