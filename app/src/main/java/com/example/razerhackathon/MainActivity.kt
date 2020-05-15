package com.example.razerhackathon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.razerhackathon.db.testDAO

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun addData(view: View) {
        testDAO.addUserDate("Felix", "Wang","1996")
    }
}