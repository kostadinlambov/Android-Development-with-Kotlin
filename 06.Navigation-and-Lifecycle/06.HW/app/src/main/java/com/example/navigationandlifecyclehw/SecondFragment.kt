package com.example.navigationandlifecyclehw

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.navigationandlifecyclehw.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    lateinit var binding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)

        binding.btnNextFragment.setOnClickListener { view ->
            val action = SecondFragmentDirections.actionSecondFragmentToThirdFragment()
            view.findNavController().navigate(action)
        }

        return binding.root
    }

}