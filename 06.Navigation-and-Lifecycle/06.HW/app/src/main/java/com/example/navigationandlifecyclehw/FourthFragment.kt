package com.example.navigationandlifecyclehw

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.navigationandlifecyclehw.databinding.FragmentFourthBinding

class FourthFragment : Fragment() {

    lateinit var binding: FragmentFourthBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFourthBinding.inflate(inflater, container, false)

        binding.btnNextFragment.setOnClickListener { view ->
            val action = FourthFragmentDirections.actionFourthFragmentToFirstFragment()
            view.findNavController().navigate(action)
        }

        return binding.root
    }


}