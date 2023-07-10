package com.dwiki.tiketku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
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
        berandaViewModel.deletePref()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        navController = navHostFragment!!.findNavController()

        binding.bottomNav.setupWithNavController(navController)

        binding.bottomNav.setOnItemSelectedListener { item ->

        when(item.itemId){
            R.id.berandaFragment -> {
                navController.navigate(R.id.berandaFragment)
                true
            }
            R.id.riwayatFragment -> {
                navController.navigate(R.id.riwayatFragment)
                true
            }
            R.id.akunFragment -> {
                navController.navigate(R.id.akunFragment)
                true
            }
            R.id.notifikasiFragment->{
                navController.navigate(R.id.notifikasiFragment)
                true
            } else -> {
                false
            }
        }

        }

      navController.addOnDestinationChangedListener{ _, destination, _ ->
          when(destination.id){
                R.id.loginFragment, R.id.registerFragment,R.id.biodataPenumpangFragment,R.id.detailBiodataPenumpangFragment,R.id.splashFragment,R.id.hasilPencarianPFragment,R.id.hasilPencarianReturnFragment,R.id.hasilPencarianFragment,R.id.detailPenerbangan,
                R.id.detailBiodataPenumpangFragment,R.id.biodataPenumpangFragment,R.id.detailPenerbanganPulangPergi,R.id.checkout2,R.id.checkoutRoundTripFragment,R.id.paymentFragment,R.id.resetPasswordFragment -> {
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