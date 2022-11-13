package com.example.workwithapi.data.country

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country")
data class Country(
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "capital") val capital: String?,
    @ColumnInfo(name = "region") val region: String?,
    @ColumnInfo(name = "population") val population: String?,
    @ColumnInfo(name = "area") val area: String?,
    @ColumnInfo(name = "flag_png") val flagPng: String?,
    @ColumnInfo(name = "flag_svg") val flagSvg: String?,
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
