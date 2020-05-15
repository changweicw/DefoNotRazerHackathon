package com.example.razerhackathon.Models

data class DepositInfo(
    var amount: String = "",
    var accountId: String = "",
    var notes: String = "Deposit into savings account",
    var type: String = "deposit",
    var method: String = ""
    )