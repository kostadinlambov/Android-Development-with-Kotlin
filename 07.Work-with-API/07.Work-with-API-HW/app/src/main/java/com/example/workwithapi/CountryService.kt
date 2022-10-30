package com.example.workwithapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryService {
    @GET("all")
    fun getCountries(): Call<List<Country>>

    @GET("name/{name}")
    fun getCountry(@Path("name") name: String): Call<List<CountryName>>
}