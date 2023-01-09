package com.example.placeofattractions

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lib.Attraction
import com.example.lib.Event
import com.example.placeofattractions.databinding.ActivityMainBinding
import io.github.serpro69.kfaker.faker
import kotlin.random.Random

class MainActivity : AppCompatActivity(), RecyclerViewInterface {

    private lateinit var binding: ActivityMainBinding

    lateinit var app: MyApplication
    private lateinit var getContent: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = application as MyApplication

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()

        val pos = intent.getIntExtra("pos", 0)
        if (intent.getStringExtra("ok") == "ok") {
            binding.recyclerView.adapter?.notifyItemChanged(pos)
        }

        val listOfEvents = mutableListOf<Event>()
        val listOfLatitudes = listOf(46.554649, 46.056946, 48.856613, 51.507351, 44.787197)
        val listOfLongitudes = listOf(15.645881, 14.505752, 2.352222, -0.127758, 20.457273)
        val listOfLocations = listOf("Maribor", "Ljubljana", "Paris", "London", "Beograd")

        val faker = faker { }

        /*if (app.data.size < 5) {
            for (i in 1..5) {
                val countAddEvent = Random.nextInt(0, 2)
                for (j in 1..countAddEvent) {
                    listOfEvents.add(Event(faker.name.toString(), "19.12.2022"))
                }

                val name = faker.name.name()
                val location = listOfLocations[i-1]
                val latitude = listOfLatitudes[i-1]
                val longitude = listOfLongitudes[i-1]
                val info = faker.random.randomString()
                val year = faker.random.nextInt(1900, 2022)
                app.data.add(Attraction(name, location, latitude, longitude, info, year, listOfEvents))

                if (listOfEvents.isNotEmpty()) {
                    createNotify(name, info)
                }
                listOfEvents.clear()
            }
        }*/
        //app.saveToFile()
        if (app.data.size < 5) {
            app.readFromFIle()

            for (i in 0 until app.data.size) {

                val countAddEvent = Random.nextInt(0, 2)

                for (j in 1..countAddEvent) {
                    listOfEvents.add(Event(faker.name.toString(), "19.12.2022"))
                }

                app.data[i].events = listOfEvents

                if (app.data[i].events.isNotEmpty()) {
                    createNotify(app.data[i].name, app.data[i].info)
                }
            }
        }

        binding.recyclerView.adapter = MyAdapter(app.data, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        fun onActivityResult(result: ActivityResult) {
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data

                val name: String = data!!.getStringExtra("name").toString()
                val location: String = data.getStringExtra("location").toString()
                val latitude: Double = data.getDoubleExtra("latitude", 46.554649)
                val longitude: Double = data.getDoubleExtra("longitude", 46.554649)
                val info: String = data.getStringExtra("info").toString()
                val year: Int = data.getIntExtra("year", 2022)

                val myListOfEvents = mutableListOf<Event>()
                val myCountAddEvent = Random.nextInt(0, 3)

                for (i in 1..myCountAddEvent) {
                    myListOfEvents.add(Event(faker.name.toString(), "19.12.2022"))
                }

                val ok: String = data.getStringExtra("ok").toString()
                //Toast.makeText(applicationContext, ok, Toast.LENGTH_SHORT).show()

                if (ok == "ok") {
                    val position = data.getIntExtra("pos", 0)
                    app.data[position].name = name
                    //Toast.makeText(applicationContext, name, Toast.LENGTH_SHORT).show()
                    app.data[position].location = location
                    app.data[position].longitude = longitude
                    app.data[position].latitude = latitude
                    app.data[position].info = info
                    app.data[position].year = year
                    //binding.recyclerView.adapter?.notifyItemChanged(position)
                }
                else {
                    app.data.add(Attraction(name, location, longitude, latitude, info, year, myListOfEvents))
                }

                if (myListOfEvents.isNotEmpty()) {
                    createNotify(name, info)
                }

                binding.recyclerView.adapter = MyAdapter(app.data, this)
                binding.recyclerView.layoutManager = LinearLayoutManager(this)
                recreate()
            }
        }

        getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            onActivityResult(result)
        }

        //val adapter = MyAdapter(app.data, RecyclerViewInterface.onItemLongCLick())

        binding.inputBtn.setOnClickListener {
            val intent = Intent(this, InputActivity::class.java)
            getContent.launch(intent)
        }

        binding.aboutBtn.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        binding.mapBtn.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onItemLongCLick(position: Int) {
        val myDialog = AlertDialog.Builder(this)
            .setTitle("Confirm remove item")
            .setMessage("Are you sure, you want this?")
            .setPositiveButton("Yes") { _, _ ->
                app.data.removeAt(position)
                binding.recyclerView.adapter?.notifyItemRemoved(position)
            }
            .setNegativeButton("Cancel") { _, _ ->

            }
            .create()

        myDialog.show()
    }
    override fun onItemClick(position: Int) {
        //Toast.makeText(applicationContext, app.data[position].toString(), Toast.LENGTH_SHORT).show()

        val intent = Intent(this, InputActivity::class.java)
        intent.putExtra("InputName", app.data[position].name)
        intent.putExtra("InputLocation", app.data[position].location)
        intent.putExtra("InputLatitude", app.data[position].latitude)
        intent.putExtra("InputLongitude", app.data[position].longitude)
        intent.putExtra("InputInfo", app.data[position].info)
        intent.putExtra("InputYear", app.data[position].year)
        intent.putExtra("position", position)
        intent.putExtra("index", 1)
        getContent.launch(intent)
    }

    companion object {
        const val CHANNEL_ID = "com.example.placeofattractions" //my channel id
        const val TIME_ID = "TIME_ID"
        private var notificationId=0
        fun getNotificationUniqueID():Int {
            return notificationId++
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "MyTestChannel"
            val descriptionText = "Testing notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun createNotify(title: String, content: String) {

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val bitmapIcon = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.mainimage)

        val myPendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(title)
            .setContentText(content)
            .setLargeIcon(bitmapIcon)
            .setGroup("Events")
            .setContentIntent(myPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(getNotificationUniqueID(),builder.build())
    }

}