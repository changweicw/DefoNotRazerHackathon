package com.example.razerhackathon

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.razerhackathon.db.testDAO
import com.example.razerhackathon.global.redirectPage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiBtn = findViewById<Button>(R.id.buttonTestAPI)
        apiBtn.setOnClickListener {
            sendGet(it)
        }
    }

    fun addData(view: View) {
        testDAO.addUserDate("Felix", "Wang","1996")
    }

    fun sendGet(view: View) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(view.context)
        val url = "https://razerhackathon.sandbox.mambu.com//api/clients/8a8e870b7217403d0172174bc9ca021a"
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
                headers["Authorization"] = "Basic VGVhbTg2OnBhc3NDQzdERjNBM0I="
                headers["Content-Type"] = "application/json"
                headers["Cookie"] = "AWSALB=Og6d/DwVsf4eXzUgKs0LOZy6I4qF0WLofyUxtYs6VocqXMRvd6gOA7l3I6TXwNkdXZH/w620GVKlI85OfsFi21I9HwZSDHUZhUe2Wtm1qPkFaaFsFJGblyRpadCI; AWSALBCORS=Og6d/DwVsf4eXzUgKs0LOZy6I4qF0WLofyUxtYs6VocqXMRvd6gOA7l3I6TXwNkdXZH/w620GVKlI85OfsFi21I9HwZSDHUZhUe2Wtm1qPkFaaFsFJGblyRpadCI"
                return headers
            }
        }
        // Add the request to the RequestQueue.
        queue.add(stringRequest)

    }


    override fun onDestroy() {
        super.onDestroy()
    }


    fun buttonSignOut(view: View) {
        Firebase.auth.signOut()
        startActivity(redirectPage.signInActivity(this))
        finish()
    }

}
