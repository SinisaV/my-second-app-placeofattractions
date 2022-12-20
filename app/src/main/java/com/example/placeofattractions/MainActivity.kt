package com.example.placeofattractions

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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

        val pos = intent.getIntExtra("pos", 0)
        if (intent.getStringExtra("ok") == "ok") {
            binding.recyclerView.adapter?.notifyItemChanged(pos)
        }

        val listOfEvents = mutableListOf<Event>()
        val countAddEvent = Random.nextInt(1, 3)

        val faker = faker { }

        for (i in 1..countAddEvent) {
            listOfEvents.add(Event(faker.name.toString(), "19.12.2022"))
        }

        if (app.data.size < 5) {
            for (i in 1..5) {
                val name = faker.name.name()
                val location = faker.address.city()
                val info = faker.random.randomString()
                val year = faker.random.nextInt(1900, 2022)
                app.data.add(Attraction(name, location, info, year, listOfEvents))
            }
        }

        binding.recyclerView.adapter = MyAdapter(app.data, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        fun onActivityResult(result: ActivityResult) {
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data

                val name: String = data!!.getStringExtra("name").toString()
                val location: String = data.getStringExtra("location").toString()
                val info: String = data.getStringExtra("info").toString()
                val year: Int = data.getIntExtra("year", 2022)

                val myListOfEvents = mutableListOf<Event>()
                val myCountAddEvent = Random.nextInt(1, 3)

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
                    app.data[position].info = info
                    app.data[position].year = year
                    //binding.recyclerView.adapter?.notifyItemChanged(position)
                }
                else {
                    app.data.add(Attraction(name, location, info, year, myListOfEvents))
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
        intent.putExtra("InputInfo", app.data[position].info)
        intent.putExtra("InputYear", app.data[position].year)
        intent.putExtra("position", position)
        intent.putExtra("index", 1)
        getContent.launch(intent)
    }
}