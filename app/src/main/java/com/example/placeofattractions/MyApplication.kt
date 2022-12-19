package com.example.placeofattractions

import android.app.Application
import com.example.lib.Attraction

class MyApplication : Application() {
    lateinit var data: MutableList<Attraction>

    override fun onCreate() {
        super.onCreate()
        data = mutableListOf()
    }
}