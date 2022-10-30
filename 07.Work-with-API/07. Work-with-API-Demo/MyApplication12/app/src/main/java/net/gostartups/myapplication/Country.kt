package net.gostartups.myapplication

/**
 * Created by borisnikolov on 24 Oct 2022.
 */
data class Country(
    var name: String,
    var capital: String,
    var flags: Flags
)

data class Flags(
    var svg: String,
    var png: String
)
