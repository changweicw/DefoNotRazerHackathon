package com.example.razerhackathon.db

import android.util.Log
import com.example.razerhackathon.Models.product
import com.example.razerhackathon.global.constants
import com.example.razerhackathon.global.constants.Companion.db
import kotlinx.coroutines.tasks.await

class productDAO {
    companion object{
        suspend fun getAllProducts() : ArrayList<product>{

            val productList: ArrayList<product> = arrayListOf()
            val querySnapshot = constants.db.collection("products").get().await()
            for (data in querySnapshot){
                val productObj = data.toObject(product::class.java)
                productObj.productId = data.id
                productList.add(productObj)
            }

            return productList
        }

        suspend fun getProductsByCategory(category : String)  : ArrayList<product>{
            val productList: ArrayList<product> = arrayListOf()
            val querySnapshot = constants.db.collection("products").whereEqualTo("category", category).get().await()
            for (data in querySnapshot){
                val productObj = data.toObject(product::class.java)
                productObj.productId = data.id
                productList.add(productObj)
            }

            return productList

        }

        suspend fun getAllUserProducts(userId : String) : ArrayList<product>{
            val productList: ArrayList<product> = arrayListOf()
            val querySnapshot = constants.db.collection("users").document(userId).collection("products").get().await()
            for (data in querySnapshot){
                val productObj = data.toObject(product::class.java)
                productObj.productId = data.id
                productList.add(productObj)
            }
            return productList
        }

        suspend fun getProductById(productId : String) : product? {
            val data = constants.db.collection("products").document(productId).get().await()

            val productObj : product? = data.toObject(product::class.java)
            if (productObj != null) {
                productObj.productId = data.id
            }
            return productObj
        }

        fun insertProductToUser( userId : String, product : product){
            db.collection("users").document(userId).collection("products").add(product)
                .addOnSuccessListener { documentReference ->
                    Log.d(constants.logTestDAO, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(constants.logTestDAO, "Error adding document", e)
                }
        }
    }
}