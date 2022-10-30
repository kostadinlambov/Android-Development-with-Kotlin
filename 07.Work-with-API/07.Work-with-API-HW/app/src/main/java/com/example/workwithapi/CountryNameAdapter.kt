package com.example.workwithapi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.workwithapi.databinding.CountryNameItemBinding
import com.google.android.material.snackbar.Snackbar

class CountryNameAdapter(private val countries: List<CountryName>) :
    RecyclerView.Adapter<CountryNameAdapter.CountryNameViewHolder>() {

    class CountryNameViewHolder(val binding: CountryNameItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryNameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CountryNameItemBinding.inflate(layoutInflater, parent, false)

        return CountryNameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryNameViewHolder, position: Int) {
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

        holder.binding.root.setOnClickListener {
            Snackbar.make(it, currentCountry.name, Snackbar.LENGTH_LONG).show()
        }

    }

    override fun getItemCount() = countries.size
}