package com.example.razerhackathon

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.media.Image
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.razerhackathon.Models.DepositInfo
import com.example.razerhackathon.Models.monstie
import com.example.razerhackathon.OkHttp.OkHttpRequestHandler
import com.example.razerhackathon.global.constants
import com.example.razerhackathon.global.constants.Companion.PREF_NAME
import com.example.razerhackathon.global.redirectPage
import com.example.razerhackathon.global.sharedPref
import com.example.razerhackathon.global.toast
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class TopUpMethodActivity : AppCompatActivity() {

    private lateinit var amountEntered: String
    private lateinit var currBalance: String
    private lateinit var shared: SharedPreferences

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

        shared = getSharedPreferences(constants.PREF_NAME, Context.MODE_PRIVATE)
        currBalance = shared!!.getString(constants.BALANCE, "")!!
        Toast.makeText(this, "User Balance:" + currBalance, Toast.LENGTH_SHORT).show()

        val backButtonTopUp = findViewById<ImageButton>(R.id.redirectTopUpAmt)
        backButtonTopUp.setOnClickListener {
            val tempIntent = Intent(it.context, TopUpActivity::class.java)
            startActivity(tempIntent)
        }
    }

    fun attachMethodListener(view: View){

        val methodDbs = view.findViewById<LinearLayout>(R.id.dbsSelect)
        val methodOcbc = view.findViewById<LinearLayout>(R.id.ocbcSelect)
        val methodUob = view.findViewById<LinearLayout>(R.id.uobSelect)
        val methodCiti = view.findViewById<LinearLayout>(R.id.citiSelect)
        val methodSc = view.findViewById<LinearLayout>(R.id.scSelect)

        /**
         * Only DBS option shall be activated for deposit testing purposes due to time constraints
         * Will run method to update entry via Mambu API
         * Will launch RazerPayActivity after onClick triggers
         * Due to Error 6 - Internal Error
         * Handler shall include SharedPref storage as a make-shift account balance storage
         */
        methodDbs.setOnClickListener {
            depositViaBank.method = "DBS"
            depositViaBank.amount = amountEntered
            if (amountEntered.toLong() > 20.0) {
                MainScope().launch {
                    monstie.getMonstieListByRarity(shared.getString(constants.USERNAME, "")!!)
                }
            }
            OkHttpRequestHandler.depositBalance(it, depositViaBank)
            val newBalance = currBalance.toDouble() + amountEntered.toLong()
            val sharedPref = sharedPref(this)
            sharedPref.putValue(constants.BALANCE, newBalance.toString() + 0)
            sharedPref.putValue(constants.BALANCETRACKER, newBalance.toString() + 0)
            sharedPref.commit()
            Toast.makeText(it.context, "New Balance: " + shared.getString(constants.BALANCE, ""), Toast.LENGTH_SHORT).show()
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
