package com.example.workwithapi.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.workwithapi.data.country.Country
import com.example.workwithapi.data.country.CountryDao
import com.example.workwithapi.data.dataInfo.DataInfo
import com.example.workwithapi.data.dataInfo.DataInfoDao
import com.example.workwithapi.data.user.User
import com.example.workwithapi.data.user.UserDao

@Database(entities = [Country::class, User::class, DataInfo::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao
    abstract fun userDao(): UserDao
    abstract fun dataInfo(): DataInfoDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "country_db"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}