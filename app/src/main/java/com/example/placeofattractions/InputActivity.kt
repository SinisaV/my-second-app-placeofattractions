package com.example.placeofattractions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import com.example.placeofattractions.databinding.ActivityInputBinding

class InputActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputBinding
    lateinit var app: MyApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data = Intent()
        app = application as MyApplication

        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val index = intent.getIntExtra("index", 0)

        if (index == 1) {
            val changeName = intent.getStringExtra("InputName")
            val changeLocation = intent.getStringExtra("InputLocation")
            val changeInfo = intent.getStringExtra("InputInfo")
            val changeYear = intent.getIntExtra("InputYear", 2000)

            binding.AttractionNameEditText.setText(changeName)
            binding.AttractionLocationEditText.setText(changeLocation)
            binding.AttractionInfoEditText.setText(changeInfo)
            binding.AttractionYearEditText.setText(changeYear.toString())

            val position = intent.getIntExtra("position",0)
            //Toast.makeText(applicationContext, position.toString(), Toast.LENGTH_SHORT).show()

            val saveBtnText =getString(R.string.saveBtn)
            binding.addBtn.text = saveBtnText

            data.putExtra("ok", "ok")
            data.putExtra("pos", position)

        }
        binding.addBtn.setOnClickListener {
            if (binding.AttractionNameEditText.text.isBlank() || binding.AttractionLocationEditText.text.isBlank()
                || binding.AttractionInfoEditText.text.isBlank() || binding.AttractionYearEditText.text.isBlank()) {

                val inputError =getString(R.string.toastInputError)
                val myToast = Toast.makeText(applicationContext, inputError, Toast.LENGTH_SHORT)
                myToast.setGravity(Gravity.CENTER, 0, 0)
                myToast.show()

            }
            else {

                if (binding.AttractionYearEditText.text.toString().toInt() > 2022) {
                    val inputYearError =getString(R.string.toastInputYearError)
                    Toast.makeText(applicationContext, inputYearError, Toast.LENGTH_SHORT).show()
                    binding.AttractionYearEditText.text.clear()
                }
                else {
                    val name: String = binding.AttractionNameEditText.text.toString()
                    val location: String = binding.AttractionLocationEditText.text.toString()
                    val info: String = binding.AttractionInfoEditText.text.toString()
                    val year: Int = binding.AttractionYearEditText.text.toString().toInt()

                    //app.data.add(Attraction(name, location, info, year, listOfEvents))
                    //Toast.makeText(applicationContext, app.data.size.toString(), Toast.LENGTH_SHORT).show()

                    binding.AttractionNameEditText.text.clear()
                    binding.AttractionLocationEditText.text.clear()
                    binding.AttractionInfoEditText.text.clear()
                    binding.AttractionYearEditText.text.clear()

                    data.putExtra("name", name)
                    data.putExtra("location",location)
                    data.putExtra("info", info)
                    data.putExtra("year", year)
                    //data.putExtra("ok", "notOk")

                    setResult(RESULT_OK, data)
                    finish()
                }
            }
        }
    }
}