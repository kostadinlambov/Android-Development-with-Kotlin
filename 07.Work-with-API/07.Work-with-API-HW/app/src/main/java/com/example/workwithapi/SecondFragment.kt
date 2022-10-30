package com.example.workwithapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.workwithapi.databinding.FragmentSecondBinding
import com.google.android.material.snackbar.Snackbar
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit.*
import retrofit2.converter.gson.GsonConverterFactory

class SecondFragment : Fragment() {

    lateinit var binding: FragmentSecondBinding

    private val args: SecondFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)

        binding.btnNextFragment.setOnClickListener { view ->
            val action = SecondFragmentDirections.actionSecondFragmentToFirstFragment()
            view.findNavController().navigate(action)
        }

        val retrofit = Builder()
            .baseUrl("https://restcountries.com/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()

        val countryService = retrofit.create(CountryService::class.java)
        val countryRepository = CountriesRepository(countryService)
        val countryName = args.countryName

        println("Current country name: $countryName")
        countryRepository.getCountry(countryName).enqueue(object : Callback<List<CountryName>> {
            override fun onResponse(
                call: Call<List<CountryName>>,
                response: Response<List<CountryName>>
            ) {
                val countryNameList: List<CountryName> = response.body() ?: return
                val currentCountry = countryNameList[0]

                binding.country = currentCountry.name
                binding.capital = "Capital: ${currentCountry.capital}"
                binding.region = "Region: ${currentCountry.region}"
                binding.population= "Population: ${currentCountry.population}"
                binding.area= "Area: ${currentCountry.area}"

                Glide
                    .with(binding.root.context)
                    .load(currentCountry.flags.png)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(binding.ivFlag)

            }

            override fun onFailure(call: Call<List<CountryName>>, t: Throwable) {
                Snackbar.make(
                    binding.root,
                    "Failed to fetch info for country: $countryName",
                    Snackbar.LENGTH_LONG
                )
                    .show()
            }
        })

        return binding.root

    }
}