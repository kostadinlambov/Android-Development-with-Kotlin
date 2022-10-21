package com.example.androidvisualslab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidvisualslab.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
//        setContentView(R.layout.activity_main)
        binding.myTextView.text = "New text"
        binding.myTextView.textSize = 18.8f

        binding.myButton.setOnClickListener{
            binding.myTextView.text = Random().nextInt().toString()
            binding.myFirstVar = Random().nextDouble().toString()
        }



    }
}