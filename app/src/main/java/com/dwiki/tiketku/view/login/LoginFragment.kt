package com.dwiki.tiketku.view.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dwiki.tiketku.R
import com.dwiki.tiketku.databinding.FragmentLoginBinding
import com.dwiki.tiketku.util.Status
import com.dwiki.tiketku.viewmodel.LoginViewModel
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import org.json.JSONTokener
import kotlin.math.log
import kotlin.reflect.typeOf

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding:FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentLoginBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmailLogin.text.toString()
            val password = binding.etPassword.text.toString()
            loginViewModel.loginResult(email,password).observe(viewLifecycleOwner){
                when(it.status){
                    Status.SUCCESS ->{
                        val loginResponse = it.data
                        if (loginResponse != null){
                            loginViewModel.saveLoginState(true)
                            loginViewModel.saveTokenPreferences(loginResponse.token)
                            findNavController().navigate(R.id.action_loginFragment_to_berandaFragment)
                        }
                    }
                    Status.LOADING -> {
                        Log.d("Login Fragment", "Loading Login")
                    }
                    Status.ERROR ->{
                        val jsonObject = JSONTokener(it.message).nextValue() as JSONObject
                        val msg = jsonObject.getString("message")
                        Log.e("Login Fragment", msg)
                        when(msg){
                            "Pasword salah!" -> {
                                errorLogin(binding.etPassword)
                                FancyToast.makeText(requireContext(),"Maaf, kata sandi salah",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show()

                            }
                            "Email tidak ditemukan" -> {
                                errorLogin(binding.etEmailLogin)
                                FancyToast.makeText(requireContext(),"Alamat email tidak terdaftar!",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show()
                            }
                        }

                    }
                }
            }
        }

        binding.txtDaftar.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.txtLupaPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_resetPasswordFragment)
        }
    }

    fun errorLogin(view: EditText){
        view.requestFocus()
        view.background = resources.getDrawable(R.drawable.share_retangle_error)
    }

}