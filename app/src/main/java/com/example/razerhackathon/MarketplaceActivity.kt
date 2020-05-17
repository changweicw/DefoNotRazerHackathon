package com.example.razerhackathon


import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.razerhackathon.Models.ClientInfo
import com.example.razerhackathon.adapters.newExpeditionListAdapter
import com.example.razerhackathon.adapters.productAdapter
import com.example.razerhackathon.db.productDAO
import com.example.razerhackathon.db.userDAO
import com.example.razerhackathon.global.constants
import com.example.razerhackathon.global.redirectPage
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class MarketplaceActivity : AppCompatActivity() {
    lateinit var marketplaceCoins : TextView
    lateinit var username : String
    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marketplace)
        // Hiding the Activity Bar
        getSupportActionBar()!!.hide();
        // Shared Preference
        val shared = getSharedPreferences(constants.PREF_NAME, Context.MODE_PRIVATE)
        username = shared.getString(constants.USERNAME, "")!!
        val first_name = shared.getString(constants.FIRST_NAME, "")

        /** Setting the name **/
        val userName : TextView = findViewById(R.id.userName)
        userName.text = first_name

        /** LOGOUT **/
        val buttonLogout : ImageView = findViewById(R.id.buttonLogout)
        buttonLogout.setOnClickListener{
            ClientInfo.clearShared(this)
            startActivity(redirectPage.landingPage(this))
            finish()
        }


        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation2)
        bottom_navigation.setSelectedItemId(R.id.menuMarketPlace);
        /** Menu navigation **/
        bottom_navigation.setOnNavigationItemSelectedListener(
            object : BottomNavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    when (item.getItemId()) {
                        R.id.menuRazorPay -> {
                            startActivity(redirectPage.razerPayActivity(this@MarketplaceActivity))
                            finish()
                        }
                    }
                    return true
                }
            })

        marketplaceCoins = findViewById<TextView>(R.id.marketplaceCoins)
        val ctx = this
        // Getting populating the recyclerview
        MainScope().launch {

            // Getting the number of coins.
            val coins = userDAO.getCoins(username)
            marketplaceCoins.text = coins.toString()

            val featuredProductsList = productDAO.getProductsByCategory("featured")
            val popularProductsList = productDAO.getProductsByCategory("popular")

            val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewMarketplaceFeatured).apply {
                layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
                adapter = productAdapter(featuredProductsList, ctx!!)
            }

            val recyclerView2 = findViewById<RecyclerView>(R.id.recyclerViewMarketplacePopular).apply {
                layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
                adapter = productAdapter(popularProductsList, ctx!!)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        MainScope().launch {

            // Getting the number of coins.
            val coins = userDAO.getCoins(username)
            marketplaceCoins.text = coins.toString()

        }
    }

    fun redemptionBtn(view: View) {
        startActivity(redirectPage.redemption(this))
    }
}
