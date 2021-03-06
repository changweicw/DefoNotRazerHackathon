package com.example.razerhackathon.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.razerhackathon.Models.monstie
import com.example.razerhackathon.R



class monstieListAdapter(private val monstieList : ArrayList<monstie>, context: Context) : RecyclerView.Adapter<monstieViewHolder>() {

    val currContext = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): monstieViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_monsters_list, parent, false) as View
        return monstieViewHolder(itemView, this)
    }

    override fun getItemCount(): Int {
        return monstieList.size
    }

    override fun onBindViewHolder(holder: monstieViewHolder, position: Int) {
        holder.textViewMonstieName.text = monstieList[position].name
        holder.textViewMonstieDescription.text = monstieList[position].description
        val starsArray : ArrayList<ImageView> = arrayListOf(holder.imgStar1, holder.imgStar2, holder.imgStar3, holder.imgStar4, holder.imgStar5)
        for(i in 1..monstieList[position].rarity){
            starsArray[i-1].setImageResource(R.drawable.star_filled)
        }

        val resourceId : Int = currContext.resources.getIdentifier(monstieList[position].imageUrl,"drawable",currContext.packageName);
        holder.imgMostie.setImageResource(resourceId)
    }
}

class monstieViewHolder(itemView: View, adapter : monstieListAdapter) : RecyclerView.ViewHolder(itemView){
    val textViewMonstieName = itemView.findViewById<TextView>(R.id.textViewMonstieName)
    val textViewMonstieDescription = itemView.findViewById<TextView>(R.id.textViewMonstieDescription)
    val imgStar1 = itemView.findViewById<ImageView>(R.id.imgStar1)
    val imgStar2 = itemView.findViewById<ImageView>(R.id.imgStar2)
    val imgStar3 = itemView.findViewById<ImageView>(R.id.imgStar3)
    val imgStar4 = itemView.findViewById<ImageView>(R.id.imgStar4)
    val imgStar5 = itemView.findViewById<ImageView>(R.id.imgStar5)
    val imgMostie = itemView.findViewById<ImageView>(R.id.imgMostie)
}