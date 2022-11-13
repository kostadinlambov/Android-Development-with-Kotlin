package com.example.workwithapi.web

import retrofit2.Call

class CountriesRepository constructor(
    private val countryService: CountryService
) {
    fun getCountries(): Call<List<CountryData>>? {
        return try {
            val countries = countryService.getCountries()
            countries
        } catch (e: Exception) {
            // an error occurred, handle and act accordingly
            null
        }
    }

    fun getCountry(name: String): Call<List<CountryName>> {
        return try {
            val country = countryService.getCountry(name)
            country
        } catch (e: Exception) {
            // an error occurred, handle and act accordingly
            throw Exception("Something went wrong during fetching a country")
        }
    }
}