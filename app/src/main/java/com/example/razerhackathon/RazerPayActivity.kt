package com.example.razerhackathon


import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.razerhackathon.Models.ClientInfo
import com.example.razerhackathon.global.constants
import com.example.razerhackathon.global.redirectPage
import com.example.razerhackathon.global.toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout


class RazerPayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_razer_pay)

        // Hiding the Activity Bar
        getSupportActionBar()!!.hide();
        // Displaying the tabs and view pager.
        val viewPager : ViewPager = findViewById(R.id.viewPager)
        val tabs : TabLayout = findViewById(R.id.tabs)

        // Addin the tabs into the view pager.
        val adapter = PagerAdapter(supportFragmentManager)
        adapter.addFragment(MyAccountFragment(), "My Account")
        adapter.addFragment(MonstersFragment(), "Monsters")
        adapter.addFragment(ExpeditionFragment(), "Expedition")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

        /**
         * Testing shared preference
         */
        // Get Shared preference and toast!
        val shared = getSharedPreferences(constants.PREF_NAME, Context.MODE_PRIVATE)
        val username = shared.getString(constants.USERNAME, "")
        val first_name = shared.getString(constants.FIRST_NAME, "")
        val email = shared.getString(constants.EMAIL, "")

//        toast.toastShort(this, username!!)
//        toast.toastShort(this, email!!)
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


        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        /** Menu navigation **/
        bottom_navigation.setOnNavigationItemSelectedListener(
            object : BottomNavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    when (item.getItemId()) {
                        R.id.menuMarketPlace -> {
                            startActivity(redirectPage.marketplace(this@RazerPayActivity))
                            finish()
                        }
                    }
                    return true
                }
            })
    }

    fun buttonBellOnClick(view: View) {
        startActivity(redirectPage.mailbox(this))
    }
}
