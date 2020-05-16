package com.example.razerhackathon

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.razerhackathon.Models.DepositInfo
import com.example.razerhackathon.OkHttp.OkHttpRequestHandler
import com.example.razerhackathon.global.constants
import com.example.razerhackathon.global.constants.Companion.PREF_NAME
import com.example.razerhackathon.global.redirectPage
import com.example.razerhackathon.global.toast

class TopUpMethodActivity : AppCompatActivity() {

    private lateinit var amountEntered: String

    var depositViaBank = DepositInfo(
        "",
        "8a8e87797217aa9401721c9f645f144b",
        "Deposit into Savings Account",
        "bank"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topup_method)
        supportActionBar?.hide()

        amountEntered = intent.getStringExtra("amount")

        val anchorLayoutType = findViewById<ConstraintLayout>(R.id.topUpMethodAnchor)
        attachMethodListener(anchorLayoutType.rootView)

    }

    fun attachMethodListener(view: View){

        val methodDbs = view.findViewById<LinearLayout>(R.id.dbsSelect)
        val methodOcbc = view.findViewById<LinearLayout>(R.id.ocbcSelect)
        val methodUob = view.findViewById<LinearLayout>(R.id.uobSelect)
        val methodCiti = view.findViewById<LinearLayout>(R.id.citiSelect)
        val methodSc = view.findViewById<LinearLayout>(R.id.scSelect)

        methodDbs.setOnClickListener {
            depositViaBank.method = "DBS"
            depositViaBank.amount = amountEntered
            OkHttpRequestHandler.depositBalance(it, depositViaBank)
            Toast.makeText(view.context, "DBS Selected!", Toast.LENGTH_SHORT).show()
            val tempIntent = Intent(it.context, RazerPayActivity::class.java)
//            tempIntent.putExtra("amount", inputAmount.text.toString())
            startActivity(tempIntent)
        }

        methodOcbc.setOnClickListener {
            Toast.makeText(view.context, "OCBC Selected!", Toast.LENGTH_SHORT).show()
        }

        methodUob.setOnClickListener {
            Toast.makeText(view.context, "UOB Selected!", Toast.LENGTH_SHORT).show()
        }

        methodCiti.setOnClickListener {
            Toast.makeText(view.context, "Citibank Selected!", Toast.LENGTH_SHORT).show()
        }

        methodSc.setOnClickListener {
            Toast.makeText(view.context, "Standard Chartered Selected!", Toast.LENGTH_SHORT).show()
        }

    }

}
