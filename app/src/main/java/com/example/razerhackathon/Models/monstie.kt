package com.example.razerhackathon.Models

import com.example.razerhackathon.db.monstieDAO
import com.example.razerhackathon.global.constants
import kotlin.random.Random

class monstie(
    var name : String = "",
    var description : String = "",
    var rarity : Int = 0,
    var imageUrl : String = "") {

    companion object{

        suspend fun getMonstieListByRarity(userId : String) {
            var rarity = 1
            val randomizedNumber : Int = Random.nextInt(100)
            if(randomizedNumber <= 50) rarity = constants.COMMON
            else if (randomizedNumber <= 80) rarity = constants.RARE
            else if (randomizedNumber <= 95) rarity = constants.EPIC
            else rarity = constants.LEGENDARY

            val monstieList = monstieDAO.getMonstieByRarity(rarity)
            val monstie =  getSelectedMonstie(monstieList)
            monstieDAO.addMonstie(userId, monstie)
        }

        private fun getSelectedMonstie(monstieList : MutableList<monstie>) : monstie{
            val index = Random.nextInt(monstieList.size)
            return monstieList[index]
        }

    }
}