package com.example.navigation_and_lifecycle_lab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.navigation_and_lifecycle_lab.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {
    lateinit var binding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFirstBinding.inflate(inflater, container, false)

        binding.btnNextFragment.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.container, SecondFragment())
            transaction.addToBackStack("second_transaction")
            transaction.commit()
        }

//        binding.btnNextFragmentGraph.setOnClickListener{
//            val action = FirstFragmentDirections.firstFragmentToSecondFragment("some value")
//            val navHostFragment = parentFragmentManager.findFragmentById(R.id.container) as NavHostFragment
//            navHostFragment.navController.navigate(action)
//        }

        binding.btnNextFragmentGraph.setOnClickListener { view ->
            val action = FirstFragmentDirections.firstFragmentToSecondFragment("some value")
            view.findNavController().navigate(action)
//            this.findNavController().navigate(action)
//            Navigation.findNavController(view).navigate(action)
        }

        return binding.root
    }

}