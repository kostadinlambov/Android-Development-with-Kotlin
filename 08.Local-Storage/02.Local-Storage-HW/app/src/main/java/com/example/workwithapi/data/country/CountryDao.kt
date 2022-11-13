package com.example.workwithapi.data.country

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countries: List<Country>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(country: Country): Long

    @Query("SELECT * FROM country where id=:id")
    fun getCountryById(id: Int): Flow<Country>

    @Query("SELECT * FROM country where country.name=:name")
    suspend fun getCountryByName(name: String): Country

    @Query("SELECT * FROM country ORDER BY country.name ASC")
    fun getAllCountries(): LiveData<List<Country>>

    @Update
    suspend fun updateCountry(country: Country)

    @Delete
    suspend fun delete(country: Country)

    @Transaction
    suspend fun insertOrUpdate(country: Country){
        val countryByName = getCountryByName(country.name!!)
        updateCountry(countryByName)
    }

}