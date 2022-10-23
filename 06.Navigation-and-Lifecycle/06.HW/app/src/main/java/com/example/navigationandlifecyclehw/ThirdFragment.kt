package com.example.navigationandlifecyclehw

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.navigationandlifecyclehw.databinding.FragmentThirdBinding

class ThirdFragment : Fragment() {
    lateinit var binding: FragmentThirdBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentThirdBinding.inflate(inflater, container, false)

        binding.btnNextFragment.setOnClickListener { view ->
            val action = ThirdFragmentDirections.actionThirdFragmentToFourthFragment()
            view.findNavController().navigate(action)
        }

        return binding.root
    }

}