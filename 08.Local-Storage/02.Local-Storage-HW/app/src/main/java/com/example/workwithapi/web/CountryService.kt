package com.example.workwithapi.web

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryService {
    @GET("all")
    fun getCountries(): Call<List<CountryData>>

    @GET("name/{name}")
    fun getCountry(@Path("name") name: String): Call<List<CountryName>>
}