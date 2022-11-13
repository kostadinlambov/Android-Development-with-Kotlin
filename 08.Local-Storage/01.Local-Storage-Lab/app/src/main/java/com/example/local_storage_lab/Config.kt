package com.example.local_storage_lab

import android.app.Application
import androidx.room.Room

// Define global Objects, that can be accessed in the whole application, e.g. : Room DB
class Config : Application() {

//    var db = Room.databaseBuilder(
//        applicationContext,
//        AppDatabase::class.java,
//        "user"
//    ).build()
}