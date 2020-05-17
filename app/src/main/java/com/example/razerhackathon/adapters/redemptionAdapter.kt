package com.example.razerhackathon.adapters


import com.example.razerhackathon.Models.product
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

import com.example.razerhackathon.R
import com.example.razerhackathon.global.redirectPage


class redemptionAdapter(private val redemptionList : ArrayList<product>, context: Context) : RecyclerView.Adapter<redemptionViewHolder>() {

    val currContext = context
    val currActivity = context as Activity


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): redemptionViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_redemptions, parent, false) as View
        return redemptionViewHolder(itemView, this)
    }

    override fun getItemCount(): Int {
        return redemptionList.size
    }

    override fun onBindViewHolder(holder: redemptionViewHolder, position: Int) {

        val resourceId : Int = currContext.resources.getIdentifier(redemptionList[position].imageUrl,"drawable",currContext.packageName);
        holder.imageViewRedemption.setImageResource(resourceId)
        holder.textViewRedemptionName.text = redemptionList[position].name

    }
}

class redemptionViewHolder(itemView: View, adapter : redemptionAdapter) : RecyclerView.ViewHolder(itemView){
    val imageViewRedemption : ImageView = itemView.findViewById(R.id.imageViewRedemption)
    val textViewRedemptionName : TextView = itemView.findViewById(R.id.textViewRedemptionName)

}