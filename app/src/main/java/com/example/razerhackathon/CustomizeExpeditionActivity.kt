package com.example.razerhackathon

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.razerhackathon.Models.expedition
import com.example.razerhackathon.Models.monstie
import com.example.razerhackathon.Models.monstieLoadout
import com.example.razerhackathon.db.expeditionDAO
import com.example.razerhackathon.db.monstieDAO
import com.example.razerhackathon.global.constants
import com.example.razerhackathon.global.redirectPage
import com.example.razerhackathon.global.toast
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class CustomizeExpeditionActivity : AppCompatActivity() {

    var arrayIndex : Int = -1
    lateinit var username : String
    lateinit var expeditionId : String
    lateinit var currExpedition : expedition

    var monstieArray : ArrayList<monstie> = arrayListOf(
                                        constants.EMPTY_MONSTIE,
                                        constants.EMPTY_MONSTIE,
                                        constants.EMPTY_MONSTIE,
                                        constants.EMPTY_MONSTIE
                                        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customize_expedition)
        Log.d("ON CREATE!", "CREATED!")

        // Getting the shared preference
        val shared = getSharedPreferences(constants.PREF_NAME, Context.MODE_PRIVATE)
        username = shared.getString(constants.USERNAME, "")!!

        // Getting the expedition ID from the intent that is passed over
        expeditionId = intent.getStringExtra("expeditionId")!!
        toast.toastShort(this, "expedition Id $expeditionId")


        populateImages()

        // Getting the expedition details.
        MainScope().launch {
            currExpedition = expeditionDAO.getExpedition(expeditionId)
        }
    }

    override fun onResume() {
        super.onResume()
        // Get that specific monstie object and replace


        // Checking if everything is empty
        if(monstieLoadout.checkIfAllEmpty(this)){
            monstieArray[0] = constants.EMPTY_MONSTIE
            monstieArray[1] = constants.EMPTY_MONSTIE
            monstieArray[2] = constants.EMPTY_MONSTIE
            monstieArray[3] = constants.EMPTY_MONSTIE
            populateImages()
        }
        else{
            val monstieId : String? = monstieLoadout.checkMonsterAddedToLoadout(this, arrayIndex)
            Log.d("RESUMED!", arrayIndex.toString())
            if(monstieId != null) {
                val context = this
                MainScope().launch {
                    val monster = monstieDAO.getSpecificMonstieByUserId(username, monstieId)
                    Log.d("RESUMED!", monster.name)
                    Log.d("RESUMED!", monster.imageUrl)
                    monstieArray[arrayIndex] = monster
                    populateImages()
                }
            }
        }

    }

    private fun populateImages(){
        val imageViewMonstie1 = findViewById<ImageView>(R.id.imageViewMonstie1)
        val imageViewMonstie2 = findViewById<ImageView>(R.id.imageViewMonstie2)
        val imageViewMonstie3 = findViewById<ImageView>(R.id.imageViewMonstie3)
        val imageViewMonstie4 = findViewById<ImageView>(R.id.imageViewMonstie4)

        var id = 0
        id = resources.getIdentifier(monstieArray[0].imageUrl, "drawable", this.packageName)
        imageViewMonstie1.setImageResource(id)
        imageViewMonstie1.setOnClickListener {
            // Pass the slot number in the intent and send it over to the next activity
            val intent = redirectPage.selectMonstie(this)
            intent.putExtra(constants.SLOT_NUMBER, 1)
            arrayIndex = 0
            startActivity(intent)
        }

        id = resources.getIdentifier(monstieArray[1].imageUrl, "drawable", this.packageName)
        imageViewMonstie2.setImageResource(id)
        imageViewMonstie2.setOnClickListener {
            val intent = redirectPage.selectMonstie(this)
            intent.putExtra(constants.SLOT_NUMBER, 2)
            arrayIndex = 1
            startActivity(intent)
        }

        id = resources.getIdentifier(monstieArray[2].imageUrl, "drawable", this.packageName)
        imageViewMonstie3.setImageResource(id)
        imageViewMonstie3.setOnClickListener {
            val intent = redirectPage.selectMonstie(this)
            intent.putExtra(constants.SLOT_NUMBER, 3)
            arrayIndex = 2
            startActivity(intent)
        }

        id = resources.getIdentifier(monstieArray[3].imageUrl, "drawable", this.packageName)
        imageViewMonstie4.setImageResource(id)
        imageViewMonstie4.setOnClickListener {
            val intent = redirectPage.selectMonstie(this)
            intent.putExtra(constants.SLOT_NUMBER, 4)
            arrayIndex = 3
            startActivity(intent)
        }
    }

    fun buttonStartExpeditionOnClick(view: View) {
        // Check if the squad is full.
        if(!monstieLoadout.checkFullSquad(this)){
            toast.toastShort(this, "You do not have a full squad.")
            return
        }
        val currContext = this
        MainScope().launch {

            // Get the documentId.
            var documentId = expeditionDAO.getAvailableExpeditionId(username)

            val currentTime : Long = System.currentTimeMillis() / 1000
            val endTime : Long = currentTime + (currExpedition.timeTaken * 60) // Converting into seconds.

            // Setting the time.
            currExpedition.timeStart = currentTime
            currExpedition.timeEnd = endTime

            // Overwriting the expedition.
            expeditionDAO.setExpedition(documentId, username, currExpedition)
            expeditionDAO.setLoadout(documentId, username, monstieArray)

            // Setting the users monstie available to 'N'
            monstieDAO.batchUpdateMonstie(username, constants.NOT_AVAILABLE, monstieArray)
            toast.toastShort(currContext, "Sent on an expedition!")
            finish()

        }
        // Get any available expedition.

    }
}
