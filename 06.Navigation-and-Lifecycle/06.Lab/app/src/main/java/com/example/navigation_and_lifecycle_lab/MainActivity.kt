package com.example.navigation_and_lifecycle_lab

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.navigation_and_lifecycle_lab.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, FirstFragment())
        transaction.addToBackStack("first_transaction")
        transaction.commit()

        binding.btnNextActivity.setOnClickListener{
            val argsBundle = Bundle()
            argsBundle.putString("title", "Second Activity Title")

            var intent = Intent(this, SecondActivity::class.java)

//            intent.putExtras(argsBundle)
            intent.putExtra("title", "Second Activity Title")
            startActivity(intent)
        }

        binding.btnBrowserIntent.setOnClickListener{
            var browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))
            startActivity(browserIntent)
        }


        binding.btnNextFragmentGraph.setOnClickListener{
            val action = FirstFragmentDirections.firstFragmentToSecondFragment("some value")
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment

//            findNavController().navigate(action)
            navHostFragment.navController.navigate(action)
        }
    }
}