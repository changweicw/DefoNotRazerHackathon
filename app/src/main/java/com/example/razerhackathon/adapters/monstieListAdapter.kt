package com.example.razerhackathon.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.razerhackathon.R

class FriendItem(username : String, userid : String, profilepic : String, hasImg : Boolean){
    val userName : String = username
    val userId : String = userid
    val profilePic : String = profilepic
    val HasImg : Boolean = hasImg
}

class monstieListAdapter(private val friend_list : ArrayList<FriendItem>, context: Context) : RecyclerView.Adapter<FriendListRecViewHolder>() {

    val currContext = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendListRecViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_monsters_list, parent, false) as View
        return FriendListRecViewHolder(itemView, this)
    }

    override fun getItemCount(): Int {
        return friend_list.size
    }

    override fun onBindViewHolder(holder: FriendListRecViewHolder, position: Int) {
//        holder.textViewUserName.text = friend_list[position].userName
//        holder.textViewUserId.text = friend_list[position].userId
    }
}

class FriendListRecViewHolder(itemView: View, adapter : monstieListAdapter) : RecyclerView.ViewHolder(itemView){
//    val textViewUserName = itemView.findViewById<TextView>(R.id.)
//    val textViewUserId = itemView.findViewById<TextView>(R.id.textViewUserId)
//    val friendViewProfilePic = itemView.findViewById<s>(R.id.friendViewProfilePic)
}