package com.example.razerhackathon.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.razerhackathon.Models.expedition
import com.example.razerhackathon.R
import com.example.razerhackathon.global.constants
import com.example.razerhackathon.global.redirectPage
import com.example.razerhackathon.global.toast

class newExpeditionListAdapter(private val expeditionList : ArrayList<expedition>, context: Context, isExpeditionAvailable: Boolean) : RecyclerView.Adapter<newExpeditionViewHolder>() {

    val currContext = context
    val isExpeditionAvailable = isExpeditionAvailable

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newExpeditionViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_new_expedition, parent, false) as View
        return newExpeditionViewHolder(itemView, this)
    }

    override fun getItemCount(): Int {
        return expeditionList.size
    }

    override fun onBindViewHolder(holder: newExpeditionViewHolder, position: Int) {
        holder.textViewExpeditionName.text = expeditionList[position].name

        // Expedition TimeTaken format: 720 minutes
        val expeditionTimeTaken : String = expeditionList[position].timeTaken.toString() + " minutes"
        holder.textViewExpeditionTime.text = expeditionTimeTaken

        // Expedition TimeTaken format: 100 to 200 coins
        val expeditionRewards : String = expeditionList[position].minRewards.toString() + " to " + expeditionList[position].maxRewards.toString() + " coins"
        holder.textViewExpeditionRewards.text = expeditionRewards

        // Insert Image Binding here.
        val resourceId : Int = currContext.resources.getIdentifier(expeditionList[position].imageUrl,"drawable",currContext.packageName);
        holder.imageViewExpedition.setImageResource(resourceId)

        holder.linearLayoutNewExpedition.setOnClickListener {

            // We need to check if there are any available expeditions.
            if(isExpeditionAvailable){
                val intent = redirectPage.customizeExpedition(currContext)
                intent.putExtra("expeditionId", expeditionList[position].expeditionId)
                currContext.startActivity(intent)
            }else{
                toast.toastShort(currContext as Activity, "You already have ${constants.MAX_EXPEDITION} ongoing expeditions.")
            }

        }
    }
}

class newExpeditionViewHolder(itemView: View, adapter : newExpeditionListAdapter) : RecyclerView.ViewHolder(itemView){

    val linearLayoutNewExpedition = itemView.findViewById<LinearLayout>(R.id.linearLayoutNewExpedition)
    val textViewExpeditionName = itemView.findViewById<TextView>(R.id.textViewExpeditionName)
    val textViewExpeditionRewards = itemView.findViewById<TextView>(R.id.textViewExpeditionRewards)
    val textViewExpeditionTime = itemView.findViewById<TextView>(R.id.textViewExpeditionTime)
    val imageViewExpedition = itemView.findViewById<ImageView>(R.id.imageViewExpedition)
}