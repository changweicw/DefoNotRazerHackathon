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
import com.example.razerhackathon.Models.monstie
import com.example.razerhackathon.Models.monstieLoadout
import com.example.razerhackathon.R
import com.example.razerhackathon.global.redirectPage


class productAdapter(private val productList : ArrayList<product>, context: Context) : RecyclerView.Adapter<productViewHolder>() {

    val currContext = context
    val currActivity = context as Activity


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): productViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_shopping, parent, false) as View
        return productViewHolder(itemView, this)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: productViewHolder, position: Int) {
        val resourceId : Int = currContext.resources.getIdentifier(productList[position].imageUrl,"drawable",currContext.packageName);
        holder.imageViewMarketplace.setImageResource(resourceId)

        holder.textViewMarketplaceName.text = productList[position].name
        holder.textViewMarketplaceCompany.text = productList[position].company
        holder.textViewMarketplacePrice.text = productList[position].price.toString()

        holder.linearLayoutShopping.setOnClickListener{
            val intent = redirectPage.productDetails(currContext)
            intent.putExtra("productId", productList[position].productId)
            currContext.startActivity(intent)
        }
    }
}

class productViewHolder(itemView: View, adapter : productAdapter) : RecyclerView.ViewHolder(itemView){
    val linearLayoutShopping : LinearLayout = itemView.findViewById(R.id.linearLayoutShopping)
    val imageViewMarketplace : ImageView = itemView.findViewById(R.id.imageViewMarketplace)
    val textViewMarketplaceName : TextView = itemView.findViewById(R.id.textViewMarketplaceName)
    val textViewMarketplaceCompany : TextView = itemView.findViewById(R.id.textViewMarketplaceCompany)
    val textViewMarketplacePrice : TextView = itemView.findViewById(R.id.textViewMarketplacePrice)
}