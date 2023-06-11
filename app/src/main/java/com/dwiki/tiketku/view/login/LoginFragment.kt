package com.dwiki.tiketku.view.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.dwiki.tiketku.R
import com.dwiki.tiketku.databinding.FragmentLoginBinding
import com.dwiki.tiketku.viewmodel.LoginViewModel
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import org.json.JSONTokener
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
            val email = binding.etEmailLogin.text.toString().trim()
            val password = binding.etPasswordLogin.text.toString().trim()
            loginViewModel.getUserLogin(email, password)
            loginViewModel.userItemLogin.observe(viewLifecycleOwner) {
                if (it.token != null) {
                    Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_SHORT).show()
                }
            }
            loginViewModel.error.observe(viewLifecycleOwner) {
                Log.d("Login", it.toString())
                if (it.isNotEmpty()) {
                    val jsonObject = JSONTokener(it).nextValue() as JSONObject
                    val msg = jsonObject.getString("message")
                    FancyToast.makeText(
                        requireContext(),
                        msg,
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR,
                        false
                    ).show()
                } else {
                    FancyToast.makeText(
                        requireContext(),
                        "User belum terdaftar",
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR,
                        false
                    ).show()
                }

            }

        }
    }

}