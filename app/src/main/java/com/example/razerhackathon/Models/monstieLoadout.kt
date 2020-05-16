package com.example.razerhackathon.Models

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.razerhackathon.db.monstieDAO.Companion.getSpecificMonstieByUserId
import com.example.razerhackathon.global.constants
import com.example.razerhackathon.global.sharedPref
import com.example.razerhackathon.global.toast

class monstieLoadout {
    companion object{

        // Cleaar all monster slots
        fun clearLoadout(ctx: Activity){
            val shared = sharedPref(ctx)
            shared.putValue(constants.FIRST_SLOT, constants.EMPTY_MONSTIE.monstieId)
            shared.putValue(constants.SECOND_SLOT, constants.EMPTY_MONSTIE.monstieId)
            shared.putValue(constants.THIRD_SLOT, constants.EMPTY_MONSTIE.monstieId)
            shared.putValue(constants.FOURTH_SLOT, constants.EMPTY_MONSTIE.monstieId)
            shared.commit()

            Log.d("CLEARED LOADOUT", "Cleared Loadout!")
        }


        // Add loadout
        fun addLoadout(ctx: Activity, monstieId : String, slotNumber : Int){
            val shared = sharedPref(ctx)
            if(slotNumber == 1) shared.putValue(constants.FIRST_SLOT, monstieId)
            else if (slotNumber == 2) shared.putValue(constants.SECOND_SLOT, monstieId)
            else if (slotNumber == 3) shared.putValue(constants.THIRD_SLOT, monstieId)
            else if (slotNumber == 4) shared.putValue(constants.FOURTH_SLOT, monstieId)
            shared.commit()
        }

        fun returnLoadoutAsArray(ctx: Context) : Array<String>{
            val shared = ctx.getSharedPreferences(constants.PREF_NAME, Context.MODE_PRIVATE)
            val firstSlot = shared.getString(constants.FIRST_SLOT, "")!!
            Log.d("CHECK LOADOUT", firstSlot)
            val secondSlot = shared.getString(constants.SECOND_SLOT, "")!!
            Log.d("CHECK LOADOUT", secondSlot)
            val thirdSlot = shared.getString(constants.THIRD_SLOT, "")!!
            Log.d("CHECK LOADOUT", thirdSlot)
            val fourthSlot = shared.getString(constants.FOURTH_SLOT, "")!!
            Log.d("CHECK LOADOUT", fourthSlot)

            //toast.toastShort(ctx as Activity, "firstSlot: $firstSlot \n secondSlot: $secondSlot \n thirdslot : $thirdSlot \n fourthslot : $fourthSlot" )
            val arrayMonstieId : Array<String> = arrayOf(firstSlot, secondSlot, thirdSlot, fourthSlot)
            return arrayMonstieId
        }

        fun removeSelectedLoadout(ctx: Context, monstieArray : ArrayList<monstie>) : ArrayList<monstie>{
            // Getting the stuff to remove.
            val loadOutArray = returnLoadoutAsArray(ctx)

            val indexArrayList = arrayListOf<Int>()

            // Getting the index for removal
            for (i in monstieArray.indices){
                for(monstieId in loadOutArray){
                    if(monstieArray[i].monstieId == monstieId){
                        indexArrayList.add(i)
                        break
                    }
                }
            }

            // Removing the items
            Log.d("REMOVE SELECTED LOADOUT", indexArrayList.toString())
            for(i in indexArrayList.size-1 downTo 0){
                monstieArray.removeAt(indexArrayList[i])
            }
            return monstieArray
        }

        fun checkMonsterAddedToLoadout(ctx: Context,  index : Int) : String?{
            val slotNumber = index + 1
            var value = ""
            val shared = ctx.getSharedPreferences(constants.PREF_NAME, Context.MODE_PRIVATE)
            if (slotNumber == 1)  value = shared.getString(constants.FIRST_SLOT, "")!!
            else if (slotNumber == 2)  value = shared.getString(constants.SECOND_SLOT, "")!!
            else if (slotNumber == 3)  value = shared.getString(constants.THIRD_SLOT, "")!!
            else if (slotNumber == 4)  value = shared.getString(constants.FOURTH_SLOT, "")!!

            if (value == "" || value == constants.EMPTY_MONSTIE.monstieId) return null
            return value
        }

        fun checkIfAllEmpty(ctx: Context) : Boolean{
            val loadOutArray = returnLoadoutAsArray(ctx)
            for(i in loadOutArray){
                if(i != "" && i != constants.EMPTY_MONSTIE.monstieId)
                    return false
            }
            return true
        }

        fun checkFullSquad(ctx: Context) : Boolean{
            val loadOutArray = returnLoadoutAsArray(ctx)
            for(i in loadOutArray){
                if(i == "" || i == constants.EMPTY_MONSTIE.monstieId)
                    return false
            }
            return true
        }
    }
}