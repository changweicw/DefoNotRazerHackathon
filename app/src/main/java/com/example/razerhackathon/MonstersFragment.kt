package com.example.razerhackathon

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.razerhackathon.Models.monstie
import com.example.razerhackathon.global.constants
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch



/**
 * A simple [Fragment] subclass.
 * Use the [Monsters.newInstance] factory method to
 * create an instance of this fragment.
 */
class MonstersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view : View =  inflater.inflate(R.layout.fragment_monsters, container, false)
        val buttonMonstie: Button = view.findViewById(R.id.Monstie_Test)

        val shared = activity!!.getSharedPreferences(constants.PREF_NAME, Context.MODE_PRIVATE)
        val username = shared.getString(constants.USERNAME, "")!!


        MainScope().launch {
            // Creating the recycler view
        }

        buttonMonstie.setOnClickListener {
            MainScope().launch {
                monstie.getMonstieListByRarity(username)
            }
        }


        return view
    }


}
