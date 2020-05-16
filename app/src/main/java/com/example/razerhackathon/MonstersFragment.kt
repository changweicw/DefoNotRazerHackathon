package com.example.razerhackathon

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.razerhackathon.Models.monstie
import com.example.razerhackathon.Models.monstieLoadout
import com.example.razerhackathon.adapters.monstieListAdapter
import com.example.razerhackathon.db.expeditionDAO
import com.example.razerhackathon.db.monstieDAO
import com.example.razerhackathon.global.constants
import com.example.razerhackathon.global.sharedPref
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch



/**
 * A simple [Fragment] subclass.
 * Use the [Monsters.newInstance] factory method to
 * create an instance of this fragment.
 */
class MonstersFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var username : String
    private lateinit var shared: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(R.layout.fragment_monsters, container, false)
        val buttonMonstie: Button = view.findViewById(R.id.Monstie_Test)

        shared = activity!!.getSharedPreferences(constants.PREF_NAME, Context.MODE_PRIVATE)
        username = shared.getString(constants.USERNAME, "")!!

        // RMB TO DELETE THIS
//        expeditionDAO.createEmptyExpedition(username)
        /////////////

        monstieLoadout.clearLoadout(activity!!)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewMonstie)
        refreshPage()

        buttonMonstie.setOnClickListener {
            MainScope().launch {
                monstie.getMonstieListByRarity(username)
                refreshPage()
            }
        }

        val currentAccBalance = shared.getString(constants.BALANCE, "")
        if (currentAccBalance != ""){
            val trimString = currentAccBalance!!.dropLast(3)
            if ( Integer.parseInt(trimString) < 200 ){
                val textViewDepositValue = view.findViewById<TextView>(R.id.depositValue)
                textViewDepositValue.text = currentAccBalance.dropLast(3)
                val myDepositProgressBar = view.findViewById<ProgressBar>(R.id.depositProgressBar)
                val percentageCalculator = ( Integer.parseInt(trimString) / 2 ).toString()
                Log.d("PERCENTAGE ENTERED", percentageCalculator)
                Toast.makeText(view.context, "Percentage: " + percentageCalculator, Toast.LENGTH_SHORT).show()
                myDepositProgressBar.progress = Integer.parseInt(percentageCalculator)
            } else {
                val textViewDepositValue = view.findViewById<TextView>(R.id.depositValue)
                textViewDepositValue.text = "200"
                val myDepositProgressBar = view.findViewById<ProgressBar>(R.id.depositProgressBar)
                myDepositProgressBar.progress = 100
            }

        }

        val myRedeemAnchor = view.findViewById<LinearLayout>(R.id.redeemAnchor)
        myRedeemAnchor.setOnClickListener {
            val sharedPref = shared.edit()
            sharedPref.putInt(constants.REDEEMCOUNTER, shared.getInt(constants.REDEEMCOUNTER, 0)+1)
            sharedPref.commit()
            Log.d("Check Redeem Value: ", shared.getInt(constants.REDEEMCOUNTER, 0).toString())
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        monstieLoadout.clearLoadout(activity!!)
        refreshPage()
    }

    private fun refreshPage(){
        MainScope().launch {
            // Creating the recycler view
            val monstieArrayList = monstieDAO.getMonstieByUserId(username)
            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = monstieListAdapter(monstieArrayList, context!!)
            }
        }
    }
}

