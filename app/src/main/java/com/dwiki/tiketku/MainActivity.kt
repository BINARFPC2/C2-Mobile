package com.dwiki.tiketku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.dwiki.tiketku.databinding.ActivityMainBinding
import com.dwiki.tiketku.viewmodel.BerandaViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var navController: NavController
    private val berandaViewModel: BerandaViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        berandaViewModel.deletePref()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        navController = navHostFragment!!.findNavController()

        binding.bottomNav.setupWithNavController(navController)

      navController.addOnDestinationChangedListener{ _, destination, _ ->
          when(destination.id){
                R.id.loginFragment, R.id.registerFragment -> {
                   binding.bottomNav.visibility = View.GONE
                }
              else ->{
                  binding.bottomNav.visibility = View.VISIBLE
              }
         }
      }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Main","ON Destroy")
    }
}