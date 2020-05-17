package com.example.razerhackathon

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.razerhackathon.Models.product
import com.example.razerhackathon.db.productDAO
import com.example.razerhackathon.db.userDAO
import com.example.razerhackathon.global.constants
import com.example.razerhackathon.global.redirectPage
import com.example.razerhackathon.global.toast
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var productId : String
    private lateinit var productObj : product
    private lateinit var username : String

    var balance : Int = 0

    private lateinit var linearLayoutOverlay : LinearLayout
    private lateinit var linearLayoutDialog : LinearLayout
    private var enoughCoins = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        // Hiding the Activity Bar
        getSupportActionBar()!!.hide();
        // Getting the userID from shared preference
        val shared = getSharedPreferences(constants.PREF_NAME, Context.MODE_PRIVATE)
        username = shared.getString(constants.USERNAME, "")!!

        // Overlays
        val linearLayoutOverlay : LinearLayout = findViewById(R.id.linearLayoutPDOverlay)
        val linearLayoutDialog : LinearLayout = findViewById(R.id.linearLayoutDialog)

        // Getting the expedition ID from the intent that is passed over
        productId = intent.getStringExtra("productId")!!
//        toast.toastShort(this, "product Id $productId")

        // Declaring buttons
        val buttonPDredeem : Button = findViewById(R.id.buttonPDRedeem)
        val buttonConfirm : Button = findViewById(R.id.buttonConfirm)
        val buttonCancel : Button = findViewById(R.id.buttonCancel)
        val textViewPDCost : TextView = findViewById(R.id.textViewPDCost)
        val textViewPDBalance : TextView = findViewById(R.id.textViewPDBalance)

        val backBtn : ImageView = findViewById(R.id.backCaret)
        backBtn.setOnClickListener{
            finish()
        }


        buttonPDredeem.setOnClickListener {
            textViewPDBalance.text = "Would you like to redeem this with ${productObj.price}"
            textViewPDCost.text = "Remaining balance: ${balance - productObj.price}"
            linearLayoutOverlay.visibility = View.VISIBLE
            linearLayoutDialog.visibility = View.VISIBLE
        }

        buttonConfirm.setOnClickListener {
            userDAO.setCoins(username, balance - productObj.price)
            // Add it into user redemption list.
            productDAO.insertProductToUser(username, productObj)
            startActivity(redirectPage.marketplace(this))
        }

        buttonCancel.setOnClickListener {
            linearLayoutOverlay.visibility = View.GONE
            linearLayoutDialog.visibility = View.GONE
        }
        MainScope().launch {
            productObj = productDAO.getProductById(productId)!!
            balance = userDAO.getCoins(username)
            if(balance >=  productObj.price) enoughCoins = true
            populateViews()
        }
    }



    private fun populateViews(){
        val productName : TextView = findViewById(R.id.textViewPDName)
        productName.setText(productObj.name)

        val productDescription : TextView = findViewById(R.id.textViewPDDescription)
        productDescription.setText(productObj.description)

        val price : TextView = findViewById(R.id.textViewPDPrice)
        price.setText(productObj.price.toString())

        val image : ImageView = findViewById(R.id.imageViewPD)
        val resourceId : Int = resources.getIdentifier(productObj.imageUrl,"drawable", packageName);
        image.setImageResource(resourceId)

        // Displaying and hiding of buttons.

        if (enoughCoins){
            val buttonPDRedeem : Button = findViewById(R.id.buttonPDRedeem)
            buttonPDRedeem.visibility = View.VISIBLE
        }
        else{
            val buttonPDInsufficient : Button = findViewById(R.id.buttonPDInsufficient)
            buttonPDInsufficient.visibility = View.VISIBLE
        }
    }


}
