package com.dwiki.tiketku.ResetPassword

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
import com.dwiki.tiketku.databinding.FragmentResetPasswordBinding
import com.dwiki.tiketku.util.Status
import com.dwiki.tiketku.viewmodel.LoginViewModel
import com.dwiki.tiketku.viewmodel.RegisterViewModel
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import org.json.JSONTokener


@AndroidEntryPoint
class ResetPasswordFragment : Fragment() {

    private lateinit var binding: FragmentResetPasswordBinding
    private val registViewModel: RegisterViewModel by viewModels()
    private val loginViewModel:LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = loginViewModel.getTokenPreferences()
//        resetPassword()
        binding.btnSimpan.setOnClickListener {
            val email = binding.etPassword.text.toString()
            if (binding.etPassword.length() > 0){
                registViewModel.resetPassword(email).observe(viewLifecycleOwner){
                    when(it.status){
                        Status.SUCCESS -> {
                            FancyToast.makeText(requireContext(),"Silahkan cek email anda",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show()
                            findNavController().navigate(
                                R.id.action_resetPasswordFragment_to_berandaFragment
                            )
                        }
                        Status.LOADING -> {
                            Log.d("Reset Password","Loading")
                        }
                        Status.ERROR -> {
                            val jsonObject = JSONTokener(it.message).nextValue() as JSONObject
                                val msg = jsonObject.getString("message")
                                when(msg){
                                    "Email not found" -> {
                                        errorRegister(binding.etPassword)
                                        errorToast("Email tidak ditemukan")
                                    }
                                }
                        }
                    }
                }
            } else {
                FancyToast.makeText(requireContext(),"Masukan password terlebih dahulu", FancyToast.LENGTH_SHORT, FancyToast.ERROR,false).show()
            }
        }
    }


    fun errorRegister(view: EditText){
        view.requestFocus()
        view.background = resources.getDrawable(R.drawable.share_retangle_error)
    }

    fun errorToast(msg:String){
        FancyToast.makeText(requireContext(),msg, FancyToast.LENGTH_SHORT, FancyToast.ERROR,false).show()
    }

}