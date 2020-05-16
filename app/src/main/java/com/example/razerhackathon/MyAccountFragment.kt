package com.example.razerhackathon

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.razerhackathon.Models.monstieLoadout
import com.example.razerhackathon.db.expeditionDAO

import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.razerhackathon.global.constants
import com.example.razerhackathon.global.redirectPage
import kotlinx.android.synthetic.main.fragment_my_account.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyAccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyAccountFragment : Fragment() {

    private lateinit var shared: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        monstieLoadout.clearLoadout(activity!!)

        val currView = inflater.inflate(R.layout.fragment_my_account, container, false)

        shared = currView.context.getSharedPreferences(constants.PREF_NAME, Context.MODE_PRIVATE)

        val userBal = shared.getString(constants.BALANCE, "")
        if (userBal != "") {
            val userBalanceMain = currView.findViewById<TextView>(R.id.userBalanceMain)
            userBalanceMain.text = userBal
        }

        val myTopUpBtn = currView.findViewById<ImageButton>(R.id.topUpLaunchBtn)

        myTopUpBtn?.setOnClickListener {
            val tempIntent = Intent(it.context, TopUpActivity::class.java)
            startActivity(tempIntent)
        }

        return currView

    }

    override fun onResume() {
        super.onResume()
        monstieLoadout.clearLoadout(activity!!)
    }

}
