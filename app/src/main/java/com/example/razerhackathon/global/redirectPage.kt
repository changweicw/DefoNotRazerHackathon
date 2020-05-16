package com.example.razerhackathon.global

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.razerhackathon.*

class redirectPage {
    companion object{
        fun mainActivity(ctx : Context) : Intent{
            val intent = Intent(ctx, MainActivity::class.java)
            return intent
        }

        fun signInActivity(ctx : Context) : Intent{
            val intent = Intent(ctx, SignInActivity::class.java)
            return intent
        }

        fun registerActivity(ctx : Context) : Intent{
            val intent = Intent(ctx, RegisterActivity::class.java)
            return intent
        }

        fun register2Activity(ctx : Context) : Intent{
            val intent = Intent(ctx, Register2Activity::class.java)
            return intent
        }

        fun razerPayActivity(ctx : Context) : Intent{
            val intent = Intent(ctx, RazerPayActivity::class.java)
            return intent
        }

        fun mailbox(ctx : Context) : Intent{
            val intent = Intent(ctx, MailboxActivity::class.java)
            return intent
        }

        fun customizeExpedition(ctx : Context) : Intent{
            val intent = Intent(ctx, CustomizeExpeditionActivity::class.java)
            return intent
        }

        fun selectMonstie(ctx : Context) : Intent{
            val intent = Intent(ctx, SelectMonstieActivity::class.java)
            return intent
        }

        fun expeditionCollection(ctx : Context) : Intent{
            val intent = Intent(ctx, ExpeditionCollectionActivity::class.java)
            return intent
        }
    }

}