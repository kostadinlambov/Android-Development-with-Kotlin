package com.example.android_application_basics_hw

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.android_application_basics_hw.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        val addButton = findViewById<Button>(R.id.add_button_HW)
        val resetButton = findViewById<Button>(R.id.reset_button_HW)
        val undoButton = findViewById<Button>(R.id.undo_button_HW)
        val editText = findViewById<EditText>(R.id.editText_first_HW)
        val textView = findViewById<TextView>(R.id.textview_first)

        var counter = 0
        val stack = ArrayDeque<String>()

        addButton.setOnClickListener { view ->
            val textValue = editText.text.toString()

            if (textValue.isNotBlank()) {
                counter++
                val newValue = "${counter}.${textValue}"
                textView.text = newValue
                stack.addLast(newValue)
                editText.setText("")
            }
        }

        resetButton.setOnClickListener{
            editText.setText("")
            textView.text = ""
            counter = 0
        }

        undoButton.setOnClickListener{
            if(counter > 0){
                counter--
                val textViewValue = stack.removeLastOrNull()
                val split = textViewValue?.split(".")
                editText.setText(split?.get(1) ?: "")
                textView.text = ""
            }
        }

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Enter your message", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}