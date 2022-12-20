package com.example.placeofattractions

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.lib.Attraction
import timber.log.Timber
import java.util.*

const val MY_SP_FILE_NAME = "myShared.data"

class MyApplication : Application() {
    lateinit var data: MutableList<Attraction>
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        initShared()
        if (!containsID()) {
            saveID(UUID.randomUUID().toString().replace("-", ""))
        }
        Timber.d("ID of app is ${getID()}")

        data = mutableListOf()
    }
    private fun initShared() {
        sharedPref = getSharedPreferences(MY_SP_FILE_NAME, Context.MODE_PRIVATE)
    }

    private fun saveID(id:String) {
        with(sharedPref.edit()) {
            putString("ID", id)
            apply()
        }
    }

    private fun containsID():Boolean {
        return sharedPref.contains("ID")
    }

    private fun getID():String? {
        return sharedPref.getString("ID", "DefaultNoData")
    }
}