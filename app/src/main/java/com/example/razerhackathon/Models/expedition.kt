package com.example.razerhackathon.Models

import com.example.razerhackathon.db.monstieDAO
import com.example.razerhackathon.global.constants
import kotlin.random.Random

class expedition(
    var expeditionId : String = "",
    var name : String = "",
    var maxRewards : Int = 0,
    var minRewards : Int = 0,
    var multiplier : Float = 0f,
    var chances : Int = 0,
    var timeTaken : Int = 0,
    var description :String = "",
    var imageUrl : String = "",
    var timeStart : Long = 0,
    var timeEnd : Long = 0){

    companion object{
    }
}