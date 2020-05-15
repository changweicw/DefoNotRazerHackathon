package com.example.razerhackathon.Volley

import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.example.razerhackathon.Models.ClientInfo
import com.example.razerhackathon.Models.DepositInfo
import com.example.razerhackathon.Models.JSONParser
import com.example.razerhackathon.global.constants
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject



object VolleyRequestHandler {

    private var iVolley: IVolley ?= null

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
                headers["Authorization"] = "Basic ${constants.MAMBU_ACCESS}"
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
        val docTypeHelper = "NRIC/Passport Number"
        val newClientJson = JSONObject("""
            {
            "client": {
                "firstName": ${newClient.firstName},
                "lastName": ${newClient.lastName},
                "preferredLanguage": "ENGLISH",
                "notes": "",
                "assignedBranchKey": "8a8e878e71c7a4d70171ca696b9112ef"
            },
            "idDocuments": {
                "identificationDocumentTemplateKey": "8a8e867271bd280c0171bf7e4ec71b11",
                "issuingAuthority": "Immigration Authority of Singapore",
                "documentType": "$docTypeHelper",
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
                    "value": "31f1fc79-b666-4826-9ccb-491c48619e40",
                    "customFieldID": "razerID"
                }]
            }""")
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
                headers["Authorization"] = "Basic ${constants.MAMBU_ACCESS}"
                headers["Content-Type"] = "application/json"
                return headers
            }
        }
        VolleySingleton.getInstance(view.context).addToRequestQueue(postRequest)
    }

    fun addToBalance(view: View, depositInfo: DepositInfo) {
        Log.d("RUNNING DEPOSIT", "AWAITING ASYNC TASKS")
        val queue = VolleySingleton.getInstance(view.context).requestQueue
        val url = "https://razerhackathon.sandbox.mambu.com/api/savings/${depositInfo.accountId}/transactions/"
        Log.d("ATTEMPT CONNECTION", url)

//        var customInfoHelper = JSONObject()
//
//        customInfoHelper.put("value", "unique identifier for receipt")
//        customInfoHelper.put("customFieldID", "IDENTIFIER_TRANSACTION_CHANNEL_I")
//        var myJsonArray: JSONArray = JSONArray()
//        myJsonArray.put(customInfoHelper)
//
//        var myJSONObject = JSONObject()
//        myJSONObject.put("amount", "200")
//        myJSONObject.put("notes", depositInfo.notes)
//        myJSONObject.put("type", depositInfo.type)
//        myJSONObject.put("method", depositInfo.method)
//        myJSONObject.put("customInformation", JSONArray(customInfoHelper).toString())
//        Log.d("CHECK POST PARAMS", myJSONObject.toString())
        var hashMapOne = HashMap<String, String>()
        var hashMapTwo = HashMap<String, String>()
        hashMapOne.put("value", "unique identifier for receipt")
        hashMapTwo.put("customFieldID", "IDENTIFIER_TRANSACTION_CHANNEL_I")
        var mandatoryDataHelper = ArrayList<HashMap<String, String>>()
        mandatoryDataHelper.add(hashMapOne)
        mandatoryDataHelper.add(hashMapTwo)

        var dataHelper = JSONParser(
            depositInfo.amount,
            depositInfo.notes,
            depositInfo.type,
            depositInfo.method,
            mandatoryDataHelper
        )

        var finalizedJSON = Gson().toJson(dataHelper)
        var gson = Gson()
        finalizedJSON = gson.toJson(finalizedJSON)
        Log.d("SHENMEHUISHI", finalizedJSON.toString())

        val postRequest = object: StringRequest(
            Request.Method.POST,
            url,
            Response.Listener<String> { response ->
                Log.d("HTTP POST Results", response.toString())
            },
            Response.ErrorListener {
                Log.d("HTTP POST Failed", it.toString())
            })
        {

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Basic VGVhbTg2OnBhc3NDQzdERjNBM0I="
                headers["Content-Type"] = "application/json;charset=UTF-8"
                return headers
            }

        }

        VolleySingleton.getInstance(view.context).addToRequestQueue(postRequest)
    }
}