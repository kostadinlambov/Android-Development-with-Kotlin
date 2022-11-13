package com.example.workwithapi.data.dataInfo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "data_info")
data class DataInfo(
    @ColumnInfo(name = "update_time") val updateTime: Date,
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}