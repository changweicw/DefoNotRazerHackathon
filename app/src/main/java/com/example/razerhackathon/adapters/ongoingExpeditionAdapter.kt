package com.example.razerhackathon.adapters

import android.app.Activity
import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.razerhackathon.Models.expedition
import com.example.razerhackathon.R
import com.example.razerhackathon.global.redirectPage
import com.example.razerhackathon.global.toast
import java.util.*


class ongoingExpeditionAdapter(private val expeditionList : ArrayList<expedition>, context: Context) : RecyclerView.Adapter<ongoingExpeditionViewHolder>() {

    val currContext = context
    private lateinit var mCountDownTimer: CountDownTimer
    var finishedFlag = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ongoingExpeditionViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_ongoing_expedition, parent, false) as View
        return ongoingExpeditionViewHolder(itemView, this)
    }

    override fun getItemCount(): Int {
        return expeditionList.size
    }

    override fun onBindViewHolder(holder: ongoingExpeditionViewHolder, position: Int) {
        holder.textViewExpeditionName.text = expeditionList[position].name
        holder.textViewExpeditionRewards.text = "${expeditionList[position].minRewards} to ${expeditionList[position].maxRewards} coins"
        val resourceId : Int = currContext.resources.getIdentifier(expeditionList[position].imageUrl,"drawable",currContext.packageName);
        holder.imageViewExpedition.setImageResource(resourceId)

        // Finding the time
        val currentTime : Long = System.currentTimeMillis() / 1000
        val TimeLeftInMillis: Long = (expeditionList[position].timeEnd - currentTime) * 1000
        Log.d("TimeLeft!", TimeLeftInMillis.toString())
        startTimer(TimeLeftInMillis, holder.textViewExpeditionTime, holder.textViewExpeditionTimeRemaining)

        holder.linearLayoutNewExpedition.setOnClickListener(){
            if (finishedFlag){
                val intent = redirectPage.expeditionCollection(currContext)
                intent.putExtra("expeditionId", expeditionList[position].expeditionId)
                currContext.startActivity(intent)
            }
            else{
                toast.toastShort(currContext as Activity, "Quest is not complete yet.")
            }
        }

    }

    private fun updateCountDownText(mTimeLeftInMillis: Long, targetTextView : TextView) {

        val hours: Long = (mTimeLeftInMillis / (1000*60*60)) % 24
        val minutes: Long = (mTimeLeftInMillis / (1000*60)) % 60
        val seconds: Long = (mTimeLeftInMillis / 1000) % 60

        val timeLeftFormatted: String =
            java.lang.String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
        targetTextView.setText(timeLeftFormatted)
    }

    private fun startTimer(TimeLeftInMillis : Long, targetTextView : TextView, targetTextView2 : TextView) {
        var mTimeLeftInMillis = TimeLeftInMillis
        mCountDownTimer = object : CountDownTimer(mTimeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mTimeLeftInMillis = millisUntilFinished
                updateCountDownText(mTimeLeftInMillis, targetTextView)
            }

            override fun onFinish() {
                finishedFlag = true
                targetTextView.setText("Ready for collection")
                targetTextView2.visibility = View.GONE
                // Display pop up box!

            }
        }.start()
    }
}

class ongoingExpeditionViewHolder(itemView: View, adapter : ongoingExpeditionAdapter) : RecyclerView.ViewHolder(itemView){
    val linearLayoutNewExpedition = itemView.findViewById<LinearLayout>(R.id.linearLayoutNewExpedition2)
    val textViewExpeditionName = itemView.findViewById<TextView>(R.id.textViewExpeditionName2)
    val textViewExpeditionRewards = itemView.findViewById<TextView>(R.id.textViewExpeditionRewards2)
    val textViewExpeditionTime = itemView.findViewById<TextView>(R.id.textViewExpeditionTime2)
    val imageViewExpedition = itemView.findViewById<ImageView>(R.id.imageViewExpedition2)
    val textViewExpeditionTimeRemaining = itemView.findViewById<TextView>(R.id.textViewExpeditionTimeRemaining2)

}