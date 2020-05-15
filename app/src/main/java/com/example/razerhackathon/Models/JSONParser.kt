package com.example.razerhackathon.Models

data class JSONParser (
    var amount: String = "",
    var notes: String = "Deposit into savings account",
    var type: String = "deposit",
    var method: String = "",
    var customerInformation: ArrayList<HashMap<String, String>> = ArrayList()
)