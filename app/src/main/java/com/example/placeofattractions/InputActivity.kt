package com.example.placeofattractions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import com.example.placeofattractions.databinding.ActivityInputBinding

class InputActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addBtn.setOnClickListener() {
            if (binding.AttractionNameEditText.text.isBlank() || binding.AttractionLocationEditText.text.isBlank()
                || binding.AttractionInfoEditText.text.isBlank()) {

                val inputError =getString(R.string.toastInputError)
                val myToast = Toast.makeText(applicationContext, inputError, Toast.LENGTH_SHORT)
                myToast.setGravity(Gravity.CENTER, 0, 0)
                myToast.show()

            }
            else {
                val name: String = binding.AttractionNameEditText.text.toString()
                val location: String = binding.AttractionLocationEditText.text.toString()
                val info: String = binding.AttractionInfoEditText.text.toString()

                binding.AttractionNameEditText.text.clear()
                binding.AttractionLocationEditText.text.clear()
                binding.AttractionInfoEditText.text.clear()
            }
        }
    }
}