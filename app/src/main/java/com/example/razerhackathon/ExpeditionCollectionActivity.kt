package com.example.razerhackathon

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.razerhackathon.Models.expedition
import com.example.razerhackathon.Models.monstie
import com.example.razerhackathon.db.expeditionDAO
import com.example.razerhackathon.db.monstieDAO
import com.example.razerhackathon.db.userDAO
import com.example.razerhackathon.global.constants
import com.example.razerhackathon.global.redirectPage
import com.example.razerhackathon.global.toast
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import kotlin.math.roundToInt
import kotlin.random.Random

class ExpeditionCollectionActivity : AppCompatActivity() {

    lateinit var username: String
    lateinit var expeditionId : String
    lateinit var expeditionObj : expedition
    lateinit var monstieArray : ArrayList<monstie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expedition_collection)

        // Hiding the Activity Bar
        getSupportActionBar()!!.hide();
        // Get Expedition Id
        // Getting the shared preference
        val shared = getSharedPreferences(constants.PREF_NAME, Context.MODE_PRIVATE)
        username = shared.getString(constants.USERNAME, "")!!

        // Getting the expedition ID from the intent that is passed over
        expeditionId = intent.getStringExtra("expeditionId")!!
//        toast.toastShort(this, "expedition Id $expeditionId")

        val backBtn : ImageView = findViewById(R.id.backCaret)
        backBtn.setOnClickListener{
            finish()
        }
        
        MainScope().launch {
            expeditionObj = expeditionDAO.getUserExpedition (expeditionId, username)
            monstieArray = expeditionDAO.getLoadout(expeditionId, username)


            // Need to compute rewards first.
            val initialBalance : Int = Random.nextInt(expeditionObj.minRewards, expeditionObj.maxRewards)
            
            // Add up all the multiplier
            var totalMultiplier : Double = 0.0
            for(monstie in monstieArray)
                totalMultiplier += monstie.multiplier

            val finalReward: Double = initialBalance * totalMultiplier
            val finalRewardInt = finalReward.roundToInt()


            // Free monstie and free up one slot.
            monstieDAO.batchUpdateMonstie(username, constants.AVAILABLE, monstieArray)
            expeditionDAO.setExpeditionToEmpty(expeditionId, username)

            // Save money into user's bank.
            val updatedCoins = userDAO.getCoins(username) + finalRewardInt
            userDAO.setCoins(username, updatedCoins)

            populateImages()
            populateElements(finalRewardInt.toString())

        }
    }


    private fun populateElements(reward : String){
        val textViewExpeditionCollectionName = findViewById<TextView>(R.id.textViewExpeditionCollectionName)
        textViewExpeditionCollectionName.setText(expeditionObj.name)

        val imageViewExpeditionCollection = findViewById<ImageView>(R.id.imageViewExpeditionCollection)
        var id = 0
        id = resources.getIdentifier(expeditionObj.imageUrl, "drawable", this.packageName)
        imageViewExpeditionCollection.setImageResource(id)

        val textViewExpeditionCollectionReward = findViewById<TextView>(R.id.textViewExpeditionCollectionReward)
        textViewExpeditionCollectionReward.setText(reward)
    }

    private fun populateImages(){
        val imageViewMonstie1 = findViewById<ImageView>(R.id.imageView2Monstie1)
        val imageViewMonstie2 = findViewById<ImageView>(R.id.imageView2Monstie2)
        val imageViewMonstie3 = findViewById<ImageView>(R.id.imageView2Monstie3)
        val imageViewMonstie4 = findViewById<ImageView>(R.id.imageView2Monstie4)

        var id = 0
        id = resources.getIdentifier(monstieArray[0].imageUrl, "drawable", this.packageName)
        imageViewMonstie1.setImageResource(id)

        id = resources.getIdentifier(monstieArray[1].imageUrl, "drawable", this.packageName)
        imageViewMonstie2.setImageResource(id)

        id = resources.getIdentifier(monstieArray[2].imageUrl, "drawable", this.packageName)
        imageViewMonstie3.setImageResource(id)

        id = resources.getIdentifier(monstieArray[3].imageUrl, "drawable", this.packageName)
        imageViewMonstie4.setImageResource(id)

    }

    fun collectRewardOnClick(view: View) {
        finish()
    }

}
