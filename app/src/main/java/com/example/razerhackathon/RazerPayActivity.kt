package com.example.razerhackathon

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.example.razerhackathon.Models.monstieLoadout
import com.example.razerhackathon.global.constants
import com.example.razerhackathon.global.redirectPage
import com.example.razerhackathon.global.toast
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
        val email = shared.getString(constants.EMAIL, "")

        toast.toastShort(this, username!!)
        toast.toastShort(this, email!!)
    }

    fun buttonBellOnClick(view: View) {
        startActivity(redirectPage.mailbox(this))
    }
}
