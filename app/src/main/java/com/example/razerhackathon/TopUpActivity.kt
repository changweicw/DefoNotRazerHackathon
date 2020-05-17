package com.example.razerhackathon

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.razerhackathon.global.constants
import com.example.razerhackathon.global.constants.Companion.PREF_NAME
import com.example.razerhackathon.global.toast

class TopUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topup)
        supportActionBar?.hide()

        /**
         * Testing shared preference
         */
        // Get Shared preference and toast!
        val shared = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val username = shared.getString(constants.USERNAME, "")
        val email = shared.getString(constants.EMAIL, "")

//        toast.toastShort(this, username!!)
//        toast.toastShort(this, email!!)

        val userBal = shared.getString(constants.BALANCE, "")
        if (userBal != "") {
            val userBalanceMain = findViewById<TextView>(R.id.textViewCurrBal)
            userBalanceMain.text = userBal
        }

        val myRadioGrp = findViewById<RadioGroup>(R.id.radio_group_topup)
        val radioBtnOne = findViewById<RadioButton>(R.id.radioTwenty)
        val radioBtnTwo = findViewById<RadioButton>(R.id.radioFifty)
        val radioBtnThree = findViewById<RadioButton>(R.id.radioHundred)
        val inputAmount = findViewById<TextView>(R.id.editTextAmount)
        val confirmAmountBtn = findViewById<Button>(R.id.topUpBtn)
        val redirectRazerPay = findViewById<ImageButton>(R.id.backToRazerPay)

        myRadioGrp.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            inputAmount.text = radio.text.toString()
        }

        confirmAmountBtn.setOnClickListener {

            if (inputAmount.text.toString() != ""){
                val tempIntent = Intent(it.context, TopUpMethodActivity::class.java)
                tempIntent.putExtra("amount", inputAmount.text.toString())
                startActivity(tempIntent)
            } else {
                Toast.makeText(this, "Please enter a top up amount", Toast.LENGTH_SHORT).show()
            }

        }

        redirectRazerPay.setOnClickListener {
            val tempIntent = Intent(it.context, RazerPayActivity::class.java)
            startActivity(tempIntent)
        }

    }

}
