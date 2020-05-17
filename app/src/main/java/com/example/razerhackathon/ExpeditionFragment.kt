package com.example.razerhackathon

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.razerhackathon.Models.expedition
import com.example.razerhackathon.Models.monstieLoadout
import com.example.razerhackathon.adapters.monstieListAdapter
import com.example.razerhackathon.adapters.newExpeditionListAdapter
import com.example.razerhackathon.adapters.ongoingExpeditionAdapter
import com.example.razerhackathon.db.expeditionDAO
import com.example.razerhackathon.db.monstieDAO
import com.example.razerhackathon.global.constants
import com.example.razerhackathon.global.toast
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [ExpeditionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExpeditionFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerView2 : RecyclerView
    private lateinit var username : String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_expedition, container, false)

        val shared = activity!!.getSharedPreferences(constants.PREF_NAME, Context.MODE_PRIVATE)
        username = shared.getString(constants.USERNAME, "")!!



        MainScope().launch {
            /**
             * Creating Recycler view for new expeditions.
             */

            val expeditionArrayList = expeditionDAO.getAllExpeditions()
            val ongoingExpeditionArrayList = expeditionDAO.getAllOngoingExpeditions(username)
         

            // Check if user can go for expedition anot.
            val isExpeditionAvailable = expeditionDAO.isExpeditionAvailable(username)

            recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewExpedition).apply {
                layoutManager = LinearLayoutManager(context)
                adapter = newExpeditionListAdapter(expeditionArrayList, context!!, isExpeditionAvailable)
            }

            recyclerView2 = view.findViewById<RecyclerView>(R.id.recyclerViewOngoingExpedition).apply {
                layoutManager = LinearLayoutManager(context)
                adapter = ongoingExpeditionAdapter(ongoingExpeditionArrayList, context!!)
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        monstieLoadout.clearLoadout(activity!!)

        // Refresh Recycler view for ongoing expeditions.
        MainScope().launch {
            /**
             * Creating Recycler view for new expeditions.
             */

            val expeditionArrayList = expeditionDAO.getAllExpeditions()
            val ongoingExpeditionArrayList = expeditionDAO.getAllOngoingExpeditions(username)
//            toast.toastShort(activity!!, ongoingExpeditionArrayList.size.toString())

            // Check if user can go for expedition anot.
            val isExpeditionAvailable = expeditionDAO.isExpeditionAvailable(username)

            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = newExpeditionListAdapter(expeditionArrayList, context!!, isExpeditionAvailable)
            }

            recyclerView2.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = ongoingExpeditionAdapter(ongoingExpeditionArrayList, context!!)
            }
        }
    }
}
