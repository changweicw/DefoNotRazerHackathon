package com.example.razerhackathon.Models

data class NewClientFormat(
    var firstName: String = "",
    var lastName: String = "",
    var NRIC: String = "",
    var NRIC_Issued: String = "",
    var emailAddress: String = "",
    var password: String = "",
    var confirmPassword: String = ""
)