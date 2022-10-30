package com.example.workwithapi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.workwithapi.databinding.FragmentFirstBinding
import com.google.android.material.snackbar.Snackbar
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FirstFragment : Fragment() {

    lateinit var binding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.com/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()

        val countryService = retrofit.create(CountryService::class.java)
        val countryRepository = CountriesRepository(countryService)
        countryRepository.getCountries()?.enqueue(object : Callback<List<Country>> {
            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                val countries = response.body() ?: return
                val adapter = CountryAdapter(countries)
                binding.countriesList.adapter = adapter
            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                Snackbar.make(binding.root, "Failed to fetch countries", Snackbar.LENGTH_LONG)
                    .show()
            }
        })

        return binding.root
    }
}