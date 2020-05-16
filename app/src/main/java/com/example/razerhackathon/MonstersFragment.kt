package com.example.razerhackathon

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.razerhackathon.Models.monstie
import com.example.razerhackathon.Models.monstieLoadout
import com.example.razerhackathon.adapters.monstieListAdapter
import com.example.razerhackathon.db.expeditionDAO
import com.example.razerhackathon.db.monstieDAO
import com.example.razerhackathon.global.constants
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(R.layout.fragment_monsters, container, false)
        val buttonMonstie: Button = view.findViewById(R.id.Monstie_Test)

        val shared = activity!!.getSharedPreferences(constants.PREF_NAME, Context.MODE_PRIVATE)
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

