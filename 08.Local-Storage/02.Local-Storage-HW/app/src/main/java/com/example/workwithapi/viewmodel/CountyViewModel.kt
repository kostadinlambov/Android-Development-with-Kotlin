package com.example.workwithapi.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.workwithapi.data.AppDatabase
import com.example.workwithapi.data.country.Country
import com.example.workwithapi.data.country.CountryRepository
import com.example.workwithapi.data.dataInfo.DataInfo
import com.example.workwithapi.data.dataInfo.DataInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CountyViewModel(application: Application) : AndroidViewModel(application) {

    val readAllCountries: LiveData<List<Country>>

    private val countryRepository: CountryRepository
    private val dataInfoRepository: DataInfoRepository

    init {
        val countryDao = AppDatabase.getDatabase(application).countryDao()
        val dataInfoDao = AppDatabase.getDatabase(application).dataInfo()
        countryRepository = CountryRepository(countryDao)
        dataInfoRepository = DataInfoRepository(dataInfoDao)
        readAllCountries = countryRepository.getAllCountries
//        readAllCountries = repository.getAllCountries()
    }

    fun addCountry(country: Country) {
        viewModelScope.launch(Dispatchers.IO) {
            countryRepository.addCountry(country)
        }
    }

    fun addCountries(countries: List<Country>) {
        viewModelScope.launch(Dispatchers.IO) {
            countryRepository.addCountries(countries)
        }
    }

    fun insertOrUpdateCountries(countries: List<Country>) {
        countries.forEach{ insertOrUpdate(it)}
    }

    private fun insertOrUpdate(country: Country) {
        viewModelScope.launch(Dispatchers.IO) {
            countryRepository.insertOrUpdate(country)
        }
    }

    fun getCountryById(id: Int): LiveData<Country> {
        return countryRepository.getCountryById(id).asLiveData()
    }

    suspend fun getCountryByName(name: String): Country {
        return countryRepository.getCountryByName(name)
    }

    fun getLastDataInfo(): LiveData<DataInfo>{
        return dataInfoRepository.getLast()
    }

    fun addDataInfo(dataInfo: DataInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            dataInfoRepository.addDataInfo(dataInfo)
        }
    }
}