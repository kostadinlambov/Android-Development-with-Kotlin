package com.example.workwithapi.fragments.list

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.workwithapi.data.country.Country
import com.example.workwithapi.data.dataInfo.DataInfo
import com.example.workwithapi.databinding.FragmentFirstBinding
import com.example.workwithapi.viewmodel.CountyViewModel
import com.example.workwithapi.web.CountriesRepository
import com.example.workwithapi.web.CountryData
import com.example.workwithapi.web.CountryService
import com.google.android.material.snackbar.Snackbar
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class FirstFragment : Fragment() {
    lateinit var binding: FragmentFirstBinding
    lateinit var mCountryViewModel: CountyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)

        mCountryViewModel = ViewModelProvider(this).get(CountyViewModel::class.java)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.com/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()

        val countryService = retrofit.create(CountryService::class.java)
        val countryRepository = CountriesRepository(countryService)

        val hasInternet = context?.let { checkForInternet(it) } ?: false

//        val databaseName = "country_db"
//        val deleteDatabase = context?.deleteDatabase(databaseName)

        if (hasInternet) {

            countryRepository.getCountries()?.enqueue(object : Callback<List<CountryData>> {
                override fun onResponse(
                    call: Call<List<CountryData>>,
                    response: Response<List<CountryData>>
                ) {
                    val countries = response.body() ?: return

                    val adapter = CountryDataAdapter(countries)
                    binding.countriesList.adapter = adapter
                    binding.progressBar.visibility = View.GONE

                    insertCountriesToDatabase(countries)

                    val date = Date()

                    mCountryViewModel.addDataInfo(DataInfo(date))

                    Snackbar.make(
                        binding.root,
                        "Updated countries at: $date",
                        Snackbar.LENGTH_LONG
                    ).show()

                    binding.updateTime = "Lats update time: $date"
                }

                override fun onFailure(call: Call<List<CountryData>>, t: Throwable) {
                    Snackbar.make(binding.root, "Failed to fetch countries", Snackbar.LENGTH_LONG)
                        .show()
                }
            })
        } else {
            loadCountriesFromDatabase()
        }

        return binding.root
    }

    // Save all coutries in the DB
    private fun insertCountriesToDatabase(countries: List<CountryData>) {
        val mappedCountryDataList = countries.map {
//                if(inputCheck(it.name, it.capital, it.flags.png, it.flags.svg)){
            Country(
                it.name,
                it.capital,
                it.region,
                it.population,
                it.area,
                it.flags.png,
                it.flags.svg
            )
//        }
        }

//        // Clear database
//        val databaseName = "country_db"
//        val deleteDatabase = context?.deleteDatabase(databaseName)

//        mCountryViewModel.addCountries(mappedCountryDataList)
        mCountryViewModel.insertOrUpdateCountries(mappedCountryDataList)

        println("#####  countryDataListAfterFetch: $mappedCountryDataList")
//        Toast.makeText(requireContext(), "Successfully added ${mappedCountryDataList.size} countries!", Toast.LENGTH_LONG).show()
        Snackbar.make(
            binding.root,
            "Successfully added ${mappedCountryDataList.size} countries!",
            Snackbar.LENGTH_LONG
        ).show()

        binding.count = "Country count: ${mappedCountryDataList.size}"
    }

    private fun loadCountriesFromDatabase() {
        mCountryViewModel.readAllCountries.observe(viewLifecycleOwner, Observer { countries ->
            val adapter = CountryAdapter(countries)
            binding.countriesList.adapter = adapter
            binding.progressBar.visibility = View.GONE

            Snackbar.make(
                binding.root,
                "Missing internet connection! Country list might be outdated!",
                Snackbar.LENGTH_LONG
            ).show()

            binding.count = "Country count: ${countries.size}"

        })

        mCountryViewModel.getLastDataInfo().observe(viewLifecycleOwner, Observer{
            binding.updateTime = "Lats update time: $it"
        })
    }

    private fun inputCheck(
        name: String,
        capital: String,
        flagPng: String,
        flagSvg: String
    ): Boolean {
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(capital) && TextUtils.isEmpty(flagPng) && TextUtils.isEmpty(
            flagSvg
        ))
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