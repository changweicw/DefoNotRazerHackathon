package com.example.razerhackathon.Volley

import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.razerhackathon.CONSTANTS
import com.example.razerhackathon.Models.ClientInfo
import org.json.JSONObject
import com.example.razerhackathon.global.constants.Companion.MAMBU_ACCESS


object VolleyRequestHandler {
    fun getUserProfile(view: View, mambuId: String) {
        // Get RequestQueue
        val queue = VolleySingleton.getInstance(view.context).requestQueue
        val url = "https://razerhackathon.sandbox.mambu.com/api/clients/${mambuId}"
        // Request a JSON response from the provided URL.
        val jsonRequest = object: JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val responseCut = response["encodedKey"].toString()
                Log.d("Get Encoded Key", responseCut)
                Log.d("HTTP Results", response.toString())

            },
            Response.ErrorListener {
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
        VolleySingleton.getInstance(view.context).addToRequestQueue(jsonRequest)

    }

    fun createNewProfile(view: View, newClient: ClientInfo) {

        Log.d("HTTP POST REQUEST", "Running POST request")
        val queue = VolleySingleton.getInstance(view.context).requestQueue
        val url = "https://razerhackathon.sandbox.mambu.com/api/clients/"

        val newClientJson = JSONObject("""
            {
            "client": {
                "firstName": ${newClient.firstName},
                "lastName": ${newClient.lastName},
                "preferredLanguage": "ENGLISH",
                "notes": "",
                "assignedBranchKey": "${CONSTANTS.ASSIGNED_BRANCH_KEY}"
            },
            "idDocuments": {
                "identificationDocumentTemplateKey": "8a8e867271bd280c0171bf7e4ec71b11",
                "issueingAuthority": "Immigration Authority of Singapore",
                "documentType": "NRIC/Passport Number",
                "validUntil": "${newClient.NRIC_Issued}",
                "documentId": "${newClient.NRIC}"
            },
            "addresses": [],
            "customInformation": [
                {
                    "value": "Singapore",
                    "customFieldID": "countryOfBirth"
                },
                {
                    "value": "myExtremelySecretRazerID",
                    "customFieldID": "razerID"
                }]
            }""".trimIndent())
        Log.d("Check Json Format", newClientJson.toString())

        val postRequest = object: JsonObjectRequest(
            Method.POST,
            url,
            newClientJson,
            Response.Listener { response ->
                Log.d("HTTP POST Results", response.toString())

            },
            Response.ErrorListener {
                Log.d("HTTP POST Results", it.toString())
            })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Basic ${CONSTANTS.MAMBU_ACCESS}"
                headers["Content-Type"] = "application/json"
                return headers
            }
        }
        VolleySingleton.getInstance(view.context).addToRequestQueue(postRequest)
    }

    fun createNewProfileTest(view: View, newClient: ClientInfo) {
        
    }
}