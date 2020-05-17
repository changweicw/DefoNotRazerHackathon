package com.example.razerhackathon

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.razerhackathon.Models.monstie
import com.example.razerhackathon.Models.monstieLoadout
import com.example.razerhackathon.adapters.monstieListAdapter
import com.example.razerhackathon.adapters.selectMonstieListAdapter
import com.example.razerhackathon.db.monstieDAO
import com.example.razerhackathon.global.constants
import com.example.razerhackathon.global.constants.Companion.EMPTY_MONSTIE
import com.example.razerhackathon.global.toast
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class SelectMonstieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_monstie)
        // Hiding the Activity Bar
        getSupportActionBar()!!.hide();
        // Getting the slot number from the previous intent
        val slotNumber : Int = intent.getIntExtra(constants.SLOT_NUMBER, 0)

        val shared = getSharedPreferences(constants.PREF_NAME, Context.MODE_PRIVATE)
        val username = shared.getString(constants.USERNAME, "")!!

//        val firstSlot = shared.getString(constants.FIRST_SLOT, "")!!
//        toast.toastShort(this, firstSlot)
        val backBtn : ImageView = findViewById(R.id.backCaret)
        backBtn.setOnClickListener{
            finish()
        }

//        toast.toastShort(this, username)
        val context = this
        // Receive the index number.
        MainScope().launch {

            // Creating the recycler view
            var monstieArrayList = monstieDAO.getAvailableMonstieByUserId(username)

            // Remove those monsties that are already in the loadout
            monstieArrayList = monstieLoadout.removeSelectedLoadout(context, monstieArrayList)
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewMyMonstie).apply {
                layoutManager = LinearLayoutManager(context)
                adapter = selectMonstieListAdapter(monstieArrayList, context, slotNumber)
            }
        }

        // Populate the existing monsties.


    }
}
