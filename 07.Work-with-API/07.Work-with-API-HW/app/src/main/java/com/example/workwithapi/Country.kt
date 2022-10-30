package com.example.workwithapi


data class Country(
    var name: String,
    var capital: String,
    var flags: Flags
)

data class Flags(
    var svg: String,
    var png: String
)


