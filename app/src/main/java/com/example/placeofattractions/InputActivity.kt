package com.example.placeofattractions

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import com.example.lib.Attraction
import com.example.lib.Event
import com.example.placeofattractions.databinding.ActivityInputBinding
import io.github.serpro69.kfaker.faker
import kotlin.random.Random

class InputActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputBinding
    lateinit var app: MyApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = application as MyApplication

        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addBtn.setOnClickListener() {
            if (binding.AttractionNameEditText.text.isBlank() || binding.AttractionLocationEditText.text.isBlank()
                || binding.AttractionInfoEditText.text.isBlank() || binding.AttractionYearEditText.text.isBlank()) {

                val inputError =getString(R.string.toastInputError)
                val myToast = Toast.makeText(applicationContext, inputError, Toast.LENGTH_SHORT)
                myToast.setGravity(Gravity.CENTER, 0, 0)
                myToast.show()

            }
            else {
                val name: String = binding.AttractionNameEditText.text.toString()
                val location: String = binding.AttractionLocationEditText.text.toString()
                val info: String = binding.AttractionInfoEditText.text.toString()
                val year: Int = binding.AttractionYearEditText.text.toString().toInt()


                app.data.clear()
                val listOfEvents = mutableListOf<Event>()
                val countAddEvent = Random.nextInt(1, 3)

                val faker = faker { }

                for (i in 1..countAddEvent) {
                    listOfEvents.add(Event(faker.name.toString(), "19.12.2022"))
                }

                app.data.add(Attraction(name, location, info, year, listOfEvents))
                Toast.makeText(applicationContext, app.data.toString(), Toast.LENGTH_SHORT).show()

                binding.AttractionNameEditText.text.clear()
                binding.AttractionLocationEditText.text.clear()
                binding.AttractionInfoEditText.text.clear()
                binding.AttractionYearEditText.text.clear()
            }
        }
    }
}