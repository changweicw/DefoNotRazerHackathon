package com.example.razerhackathon

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.razerhackathon.adapters.monstieListAdapter
import com.example.razerhackathon.adapters.productAdapter
import com.example.razerhackathon.adapters.redemptionAdapter
import com.example.razerhackathon.db.productDAO
import com.example.razerhackathon.db.userDAO
import com.example.razerhackathon.global.constants
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class Redemptions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_redemptions)
        // Hiding the Activity Bar
        getSupportActionBar()!!.hide();
        // Getting the userID from shared preference
        val shared = getSharedPreferences(constants.PREF_NAME, Context.MODE_PRIVATE)
        var username = shared.getString(constants.USERNAME, "")!!

        val backBtn : ImageView = findViewById(R.id.backCaret)
        backBtn.setOnClickListener{
            finish()
        }
        val ctx = this
        MainScope().launch{
            val productList = productDAO.getAllUserProducts(username)

            val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewRedemption).apply {
                layoutManager = LinearLayoutManager(context)
                adapter = redemptionAdapter(productList, context!!)
            }
        }
    }
}
