package com.example.razerhackathon.OkHttp

import android.app.Activity
import android.app.ActivityManager
import android.util.Log
import android.view.View
import com.example.razerhackathon.Models.ClientInfo
import com.example.razerhackathon.Models.DepositInfo
import com.example.razerhackathon.Models.NewAccount
import com.example.razerhackathon.Models.NewClientFormat
import com.example.razerhackathon.db.userDAO
import com.example.razerhackathon.global.constants
import com.example.razerhackathon.global.sharedPref
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

object OkHttpRequestHandler {

    fun createNewClient(userId: String, newClient: ClientInfo){
        val url = "https://razerhackathon.sandbox.mambu.com/api/clients/"
        val payload = "{" +
                "\"client\":{\"firstName\":\"${newClient.firstName}\",\"lastName\":\"${newClient.lastName}\",\"preferredLanguage\":\"ENGLISH\",\"notes\":\"TEMPORARY PLACEHOLDER\",\"assignedBranchKey\":\"${constants.ASSIGNED_BRANCH_KEY}\"}," +
                "\"idDocuments\":[{\"identificationDocumentTemplateKey\":\"8a8e867271bd280c0171bf7e4ec71b01\",\"issuingAuthority\":\"Immigration Authority of Singapore\",\"documentType\":\"NRIC/Passport Number\",\"validUntil\":\"${newClient.NRIC_Issued}\",\"documentId\":\"${newClient.NRIC}\"}]," +
                "\"addresses\":[]" +
                "}"
        var myClient = OkHttpClient()
        val requestBody = payload.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        Log.d("Payload Check", requestBody.toString())
        val request = Request.Builder()
            .method("POST", requestBody)
            .url(url)
            .addHeader("Authorization", "Basic VGVhbTg2OnBhc3NDQzdERjNBM0I=")
            .addHeader("Content-Type", "application/json")
            .build()
        myClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle this
                Log.d("OKHttp Creation Failure", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                // Handle this
                val responseBody = JSONObject(response.body!!.string())
                val generatedInfo = responseBody.getJSONObject("client")
                val generatedMambuId = generatedInfo["encodedKey"]
                Log.d("Generated Mambu ID", generatedMambuId.toString())

                userDAO.updateMambuId(userId, generatedMambuId.toString())

                val exampleNewSavings: NewAccount = NewAccount(
                    generatedMambuId.toString(),
                    7.0,
                    "2.18"
                )
                createNewAccount(userId, exampleNewSavings)
            }
        })
    }

    fun createNewAccount(userId: String, newAccount: NewAccount){
        val url = "https://razerhackathon.sandbox.mambu.com/api/savings"
        val payload = "{\"savingsAccount\":{" +
                "\"name\":\"Digital Account\"," +
                "\"accountHolderType\":\"CLIENT\"," +
                "\"accountHolderKey\":\"${newAccount.clientId}\"," +
                "\"accountState\":\"APPROVED\"," +
                "\"productTypeKey\":\"8a8e878471bf59cf0171bf6979700440\"," +
                "\"accountType\":\"CURRENT_ACCOUNT\"," +
                "\"currencyCode\":\"SGD\"," +
                "\"allowOverdraft\":\"true\"," +
                "\"overdraftLimit\":\"100\"," +
                "\"overdraftLimitSettings\":{\"interestRate\":${newAccount.overdraftInterestRate}}," +
                "\"interestSettings\":{\"interestRate\":\"${newAccount.interestRate}\"}" +
                "}}"
        var myClient = OkHttpClient()
        val requestBody = payload.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        Log.d("Payload Check", requestBody.toString())
        val request = Request.Builder()
            .method("POST", requestBody)
            .url(url)
            .addHeader("Authorization", "Basic VGVhbTg2OnBhc3NDQzdERjNBM0I=")
            .addHeader("Content-Type", "application/json")
            .build()
        myClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle this
                Log.d("Savings A/C Failure", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                // Handle this
                val responseBody = JSONObject(response.body!!.string())
                val generatedInfo = responseBody.getJSONObject("savingsAccount")
                val generatedAccountId = generatedInfo["encodedKey"]
                Log.d("Generated Account ID", generatedAccountId.toString())

                userDAO.updateAccountId(userId, generatedAccountId.toString())
            }
        })
    }

    fun depositBalance(view: View, depositInfo: DepositInfo){
        val url = "https://razerhackathon.sandbox.mambu.com/api/savings/${depositInfo.accountId}/transactions/"

        val payload = "{\"amount\":200,\"notes\":\"Deposit into savings account\",\"type\":\"DEPOSIT\",\"method\":\"bank\",\"customInformation\":[{\"value\":\"unique identifier for receipt\",\"customFieldID\":\"IDENTIFIER_TRANSACTION_CHANNEL_I\"}]}"
        var myClient = OkHttpClient()
        val requestBody = payload.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        Log.d("Payload Check", requestBody.toString())
        val request = Request.Builder()
            .method("POST", requestBody)
            .url(url)
            .addHeader("Authorization", "Basic VGVhbTg2OnBhc3NDQzdERjNBM0I=")
            .addHeader("Content-Type", "application/json")
            .build()
        myClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle this
                Log.d("OkHttp Deposit Failure", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                // Handle this
                Log.d("Deposit Successful", response.toString())
            }
        })

    }
}