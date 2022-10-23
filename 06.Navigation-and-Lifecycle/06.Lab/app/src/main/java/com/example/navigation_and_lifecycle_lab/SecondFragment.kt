package com.example.navigation_and_lifecycle_lab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.navigation_and_lifecycle_lab.databinding.FragmentFirstBinding
import com.example.navigation_and_lifecycle_lab.databinding.FragmentSecondBinding

class SecondFragment: Fragment() {
    lateinit var binding: FragmentSecondBinding

    private val args: SecondFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSecondBinding.inflate(inflater, container, false)

        binding.tvTitle.text = args.firstArg


        binding.btnGoToNextFragment.setOnClickListener { view ->
            val action = SecondFragmentDirections.secondFragmentToFirstFragment()
            view.findNavController().navigate(action)
//            this.findNavController().navigate(action)
//            Navigation.findNavController(view).navigate(action)
        }

        return binding.root
    }

}