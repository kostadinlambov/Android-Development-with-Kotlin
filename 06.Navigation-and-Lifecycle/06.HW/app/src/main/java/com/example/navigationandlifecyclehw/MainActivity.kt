package com.example.navigationandlifecyclehw

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.example.navigationandlifecyclehw.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Go to the second activity
        binding.btnNextActivity.setOnClickListener{
            val argsBundle = Bundle()
            argsBundle.putString("title", "Second Activity Title")

            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("title", "Second Activity Title")
            startActivity(intent)
        }

        // Change the Fragments from the main activity
        binding.btnNextFragment.setOnClickListener{
            val action: NavDirections
            val fragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment

            val currentFragment = fragment.childFragmentManager.fragments.last()

            action = when(currentFragment){
                is FirstFragment -> FirstFragmentDirections.actionFirstFragmentToSecondFragment()
                is SecondFragment -> SecondFragmentDirections.actionSecondFragmentToThirdFragment()
                is ThirdFragment -> ThirdFragmentDirections.actionThirdFragmentToFourthFragment()
                else -> FourthFragmentDirections.actionFourthFragmentToFirstFragment()
            }

            fragment.navController.navigate(action)
        }

    }
}