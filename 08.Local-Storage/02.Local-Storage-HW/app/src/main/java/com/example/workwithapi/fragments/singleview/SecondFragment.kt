package com.example.workwithapi.fragments.singleview

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.workwithapi.web.CountriesRepository
import com.example.workwithapi.web.CountryName
import com.example.workwithapi.web.CountryService
import com.example.workwithapi.R
import com.example.workwithapi.databinding.FragmentSecondBinding
import com.example.workwithapi.viewmodel.CountyViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit.*
import retrofit2.converter.gson.GsonConverterFactory

class SecondFragment : Fragment() {
    lateinit var binding: FragmentSecondBinding
    lateinit var mCountryViewModel: CountyViewModel
    private val args: SecondFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        mCountryViewModel = ViewModelProvider(this).get(CountyViewModel::class.java)

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

        val hasInternet = context?.let { checkForInternet(it) } ?: false

        if (hasInternet) {
            countryRepository.getCountry(countryName).enqueue(object : Callback<List<CountryName>> {
                override fun onResponse(
                    call: Call<List<CountryName>>,
                    response: Response<List<CountryName>>
                ) {
                    val countryNameList: List<CountryName> = response.body() ?: return
                    val currentCountry = countryNameList[0]

                    populateCountryModel(
                        currentCountry.name,
                        currentCountry.capital,
                        currentCountry.region,
                        currentCountry.population.toString(),
                        currentCountry.area.toString(),
                        currentCountry.flags.png
                    )

                    Snackbar.make(
                        binding.root,
                        "Loaded info for country: $countryName",
                        Snackbar.LENGTH_LONG
                    ).show()

                }

                override fun onFailure(call: Call<List<CountryName>>, t: Throwable) {
                    Snackbar.make(
                        binding.root,
                        "Failed to fetch info for country: $countryName",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            })
        } else {
            loadCountryFromDatabase(countryName)
        }

        return binding.root
    }

    private fun loadCountryFromDatabase(countryName: String) {
        lifecycleScope.launch {
            val currentCountry = mCountryViewModel.getCountryByName(countryName)
            populateCountryModel(
                countryName,
                currentCountry.capital ?: "no_capital",
                currentCountry.region ?: "no_capital",
                currentCountry.population ?: "no_population?",
                currentCountry.area ?: "no_area",
                currentCountry.flagPng ?: "no_flag"
            )

            Snackbar.make(
                binding.root,
                "Loaded info for country: $countryName from database",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun populateCountryModel(
        name: String,
        capital: String,
        region: String,
        population: String,
        area: String,
        flagPng: String,
    ) {
        binding.country = name
        binding.capital = "Capital: $capital"
        binding.region = "Region: $region"
        binding.population = "Population: $population"
        binding.area = "Area: $area"

        Glide
            .with(binding.root.context)
            .load(flagPng)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.ivFlag)
    }

    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection

        // Returns a Network object corresponding to
        // the currently active default data network.
        val network = connectivityManager.activeNetwork ?: return false

        // Representation of the capabilities of an active network.
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            // Indicates this network uses a Wi-Fi transport,
            // or WiFi has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

            // Indicates this network uses a Cellular transport. or
            // Cellular has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

            // else return false
            else -> false
        }
    }
}