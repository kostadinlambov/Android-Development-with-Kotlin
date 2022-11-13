package com.example.local_storage_lab

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.local_storage_lab.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var db: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //###### Shared preferences
        val globalSharedPreferences = getSharedPreferences(
            "my_shared_prefs",
            Context.MODE_PRIVATE
        )

        // Activity-scoped !!! shared preferences
        val activitySharedPreferences = getPreferences(Context.MODE_PRIVATE)

        with(globalSharedPreferences.edit()) {
            putString("str_key", "string_value")
            // async !!!  save the data in the local storage - the preferred method
            apply()

            // Sync !!! save the data in the local storage - for critical data
            commit()
        }

        // Get values from shared preferences
        val value = globalSharedPreferences.getString("string_key", "default_value")

        if (value == "default_value") {
            println("default value: $value")
        } else {
            println("value: $value")
        }

        //##### Room DB
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "user"
        ).build()

        val userDao = db?.userDao()

        // Start functions async
        GlobalScope.async {
            val allUser = userDao?.getAll()

            val firstName = allUser?.get(0)?.firstName ?: "No first name"
            binding.usersCount.text = "${allUser?.count()}\n$firstName"

//            val user = User(
//                1,
//                "Pesho",
//                "Peshov"
//            )
//
//            userDao?.insertAll(user)
        }

    }
}