package com.example.placeofattractions

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.lib.Attraction
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.apache.commons.io.FileUtils
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.lang.reflect.Type
import java.util.*

const val MY_FILE_NAME = "myData.json"
const val MY_SP_FILE_NAME = "myShared.data"

class MyApplication : Application() {
    lateinit var data: MutableList<Attraction>
    private lateinit var sharedPref: SharedPreferences
    private lateinit var gson: Gson
    private lateinit var file: File

    override fun onCreate() {
        super.onCreate()
        initShared()
        if (!containsID()) {
            saveID(UUID.randomUUID().toString().replace("-", ""))
        }
        Timber.d("ID of app is ${getID()}")

        data = mutableListOf()
        gson = Gson()
        file = File(filesDir, MY_FILE_NAME)
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

    fun saveToFile() {
        try {
            FileUtils.writeStringToFile(file, gson.toJson(data))
            Timber.d("Save to file.")

        }
        catch (e: IOException) {
            Timber.d("Can't save to file: ${file.path}")
        }
    }

    fun deleteFile() {
        try {
            FileUtils.deleteDirectory(file)
        }
        catch (e: IOException) {
            Timber.d("Can't delete file: ${file.path}")
        }
    }

    fun readFromFIle() {
        try {
            val tmp = file.readText()
            val myType: Type = object: TypeToken<MutableList<Attraction?>?>() {}.type
            val tmpList: MutableList<Attraction> = gson.fromJson(tmp, myType)
            data = tmpList
        }
        catch (e: IOException) {
            Timber.d("Can't read from file: ${file.path}")
        }
    }

    fun updateFile() {
        try {
            deleteFile()
            saveToFile()
        }
        catch (e: IOException) {
            Timber.d("Can't update file: ${file.path}")
        }
    }
}