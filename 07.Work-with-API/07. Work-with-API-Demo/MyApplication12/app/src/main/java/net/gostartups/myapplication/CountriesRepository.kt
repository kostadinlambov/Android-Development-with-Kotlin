package net.gostartups.myapplication

import retrofit2.Call

class CountriesRepository constructor(
    private val countryService: CountryService
) {
    fun getCountries(): Call<List<Country>>? {
        return try {
            countryService.getCountries()
        } catch (e: Exception) {
            // an error occurred, handle and act accordingly
            null
        }
    }
}