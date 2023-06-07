package com.dwiki.tiketku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.dwiki.tiketku.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        navController = navHostFragment!!.findNavController()

        binding.bottomNav.setupWithNavController(navController)

      navController.addOnDestinationChangedListener{ _, destination, _ ->
          when(destination.id){
                R.id.loginFragment -> {
                   binding.bottomNav.visibility = View.GONE
                }
              else ->{
                  binding.bottomNav.visibility = View.VISIBLE
              }
         }
      }
    }
}