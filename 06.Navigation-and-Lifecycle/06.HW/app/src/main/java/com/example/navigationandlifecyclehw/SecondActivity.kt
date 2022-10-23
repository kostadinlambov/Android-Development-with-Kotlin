package com.example.navigationandlifecyclehw

import android.app.Activity
import android.os.Bundle
import com.example.navigationandlifecyclehw.databinding.ActivitySecondBinding

class SecondActivity: Activity() {
    lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.extras?.getString("title") ?: "Default Title"
        binding.tvTitle.text = title

        binding.btnPreviousActivity.setOnClickListener{
//            var intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
            finish()
        }
    }

}