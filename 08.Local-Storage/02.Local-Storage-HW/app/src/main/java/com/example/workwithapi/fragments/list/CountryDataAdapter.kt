package com.example.workwithapi.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.workwithapi.R
import com.example.workwithapi.databinding.CountryItemBinding
import com.example.workwithapi.web.CountryData

class CountryDataAdapter(private val countries: List<CountryData>) :
    RecyclerView.Adapter<CountryDataAdapter.CountryViewHolder>() {

    class CountryViewHolder(val binding: CountryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CountryItemBinding.inflate(layoutInflater, parent, false)

        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val currentCountry = countries[position]
        holder.binding.apply {
            country = currentCountry.name
            capital = currentCountry.capital

            Glide
                .with(this.root.context)
                .load(currentCountry.flags.png)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(ivFlag)

        }

        holder.binding.root.setOnClickListener { view ->
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(currentCountry.name)
            view.findNavController().navigate(action)
        }

    }

    override fun getItemCount() = countries.size
}