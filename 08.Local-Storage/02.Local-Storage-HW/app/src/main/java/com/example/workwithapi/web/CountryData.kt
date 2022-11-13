package com.example.workwithapi.web


data class CountryData(
    var name: String,
    var capital: String,
    var region: String,
    var population: String,
    var area: String,
    var flags: Flags
)

data class Flags(
    var svg: String,
    var png: String
)


