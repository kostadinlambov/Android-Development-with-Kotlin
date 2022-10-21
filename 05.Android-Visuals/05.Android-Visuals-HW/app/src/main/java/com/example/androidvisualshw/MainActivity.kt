package com.example.androidvisualshw

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidvisualshw.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        var counter = 0
        val myImage = binding.myImage

        binding.counterValue = "Counter: $counter"
        binding.imageName = "Image: android_0"

        binding.counterButton.setOnClickListener{
            // Set counter
            counter++
            binding.counterValue = "Counter: $counter"

            val random = (1 until 6).random();

            // Set filename
            val fileName = "android_$random"
            binding.imageName = "Image: $fileName"

            // Set image
            val imageId = resources.getIdentifier(fileName, "drawable", packageName)
            println("imageId: $imageId")
            myImage.setImageResource(imageId)

        }
    }
}

