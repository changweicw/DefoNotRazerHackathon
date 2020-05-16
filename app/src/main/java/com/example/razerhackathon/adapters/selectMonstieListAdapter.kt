package com.example.razerhackathon.adapters

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.razerhackathon.Models.monstie
import com.example.razerhackathon.Models.monstieLoadout
import com.example.razerhackathon.R
import com.example.razerhackathon.global.redirectPage


class selectMonstieListAdapter(private val monstieList : ArrayList<monstie>, context: Context, slotNumber : Int) : RecyclerView.Adapter<selectMonstieViewHolder>() {

    val currContext = context
    val currActivity = context as Activity
    val slotNumber = slotNumber

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): selectMonstieViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_monsters_list, parent, false) as View
        return selectMonstieViewHolder(itemView, this)
    }

    override fun getItemCount(): Int {
        return monstieList.size
    }

    override fun onBindViewHolder(holder: selectMonstieViewHolder, position: Int) {
        holder.textViewMonstieName.text = monstieList[position].name
        holder.textViewMonstieDescription.text = monstieList[position].description
        val starsArray : ArrayList<ImageView> = arrayListOf(holder.imgStar1, holder.imgStar2, holder.imgStar3, holder.imgStar4, holder.imgStar5)
        for(i in 1..monstieList[position].rarity){
            starsArray[i-1].setImageResource(R.drawable.star_filled)
        }

        val resourceId : Int = currContext.resources.getIdentifier(monstieList[position].imageUrl,"drawable",currContext.packageName);
        holder.imgMostie.setImageResource(resourceId)

        // Onclick to redirect to previous page.
        holder.linearLayoutMonstie.setOnClickListener {
            monstieLoadout.addLoadout(currActivity, monstieList[position].monstieId, slotNumber)
            Log.d("SLOT NUMBER", slotNumber.toString())
            Log.d("ID ADDED", monstieList[position].monstieId)
            redirectPage.customizeExpedition(currContext)
            currActivity.finish()

            // Add the slot number

        }

    }
}

class selectMonstieViewHolder(itemView: View, adapter : selectMonstieListAdapter) : RecyclerView.ViewHolder(itemView){
    val linearLayoutMonstie = itemView.findViewById<LinearLayout>(R.id.linearLayoutMonstie)
    val textViewMonstieName = itemView.findViewById<TextView>(R.id.textViewMonstieName)
    val textViewMonstieDescription = itemView.findViewById<TextView>(R.id.textViewMonstieDescription)
    val imgStar1 = itemView.findViewById<ImageView>(R.id.imgStar1)
    val imgStar2 = itemView.findViewById<ImageView>(R.id.imgStar2)
    val imgStar3 = itemView.findViewById<ImageView>(R.id.imgStar3)
    val imgStar4 = itemView.findViewById<ImageView>(R.id.imgStar4)
    val imgStar5 = itemView.findViewById<ImageView>(R.id.imgStar5)
    val imgMostie = itemView.findViewById<ImageView>(R.id.imgMostie)
}