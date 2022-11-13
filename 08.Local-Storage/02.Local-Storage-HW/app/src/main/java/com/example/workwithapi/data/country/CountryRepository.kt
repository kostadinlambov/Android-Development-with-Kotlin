package com.example.workwithapi.data.country

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class CountryRepository(private val countryDao: CountryDao) {

    val getAllCountries: LiveData<List<Country>> = countryDao.getAllCountries()

    suspend fun getAllCountries() {
        countryDao.getAllCountries()
    }

    suspend fun addCountry(country: Country) {
        countryDao.insertCountry(country)
    }

    suspend fun addCountries(countries: List<Country>) {
        countryDao.insertAll(countries)
    }

    suspend fun insertOrUpdate(country: Country) {
        countryDao.insertOrUpdate(country)
    }

    fun getCountryById(id: Int): Flow<Country> {
        return countryDao.getCountryById(id)
    }

    suspend fun getCountryByName(name: String): Country {
        return countryDao.getCountryByName(name)
    }

}