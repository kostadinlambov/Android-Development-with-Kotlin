package com.example.navigationandlifecyclehw

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.navigationandlifecyclehw.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    lateinit var binding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)

        binding.btnNextFragment.setOnClickListener { view ->
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment()
            view.findNavController().navigate(action)
//            this.findNavController().navigate(action)
//            Navigation.findNavController(view).navigate(action)
        }

        return binding.root
    }


}