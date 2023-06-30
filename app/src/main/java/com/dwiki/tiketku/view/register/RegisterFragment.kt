package com.dwiki.tiketku.view.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dwiki.tiketku.R
import com.dwiki.tiketku.databinding.FragmentRegisterBinding
import com.dwiki.tiketku.util.Status
import com.dwiki.tiketku.viewmodel.RegisterViewModel
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import org.json.JSONTokener


@AndroidEntryPoint
class RegisterFragment : Fragment() {


    private lateinit var binding:FragmentRegisterBinding
    private val registViewModel:RegisterViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registAkun()
    }

    private fun registAkun() {
        binding.apply {

//            val isPasswordCorrect = false
//            val checkPass = passwordWatcher(isPasswordCorrect)

                btnRegist.setOnClickListener {
                    Log.d("Regis","Onclick")
                    val nama = etNama.text.toString()
                    val email = etEmailRegist.text.toString()
                    val pass = etPassword.text.toString()
                    val phone = etNomorTlp.text.toString()

                    if(etPassword.length() < 8){
                        errorRegister(etPassword)
                        errorToast("Password min 8 karakter!")
                    } else {
                        registViewModel.registerResult(email,pass,phone,nama).observe(viewLifecycleOwner){
                            when(it.status){
                                Status.SUCCESS -> {
                                    FancyToast.makeText(requireContext(),"Berhasil Register",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show()
                                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                                }
                                Status.ERROR -> {
                                    val jsonObject = JSONTokener(it.message).nextValue() as JSONObject
                                    val msg = jsonObject.getString("message")
                                    when(msg){
                                        "Email and password is required" -> {
                                            errorRegister(etEmailRegist)
                                            errorToast(msg)
                                        }
                                        "Email format is invalid" -> {
                                            errorRegister(etEmailRegist)
                                            errorToast(msg)
                                        }
                                        "Email already Exist" -> {
                                            errorRegister(etEmailRegist)
                                            errorToast(msg)
                                        }
                                    }
                                }
                                Status.LOADING -> {
                                    Log.d("Regis Fragment", "Loading")
                                }
                            }
                        }
                    }
            }

        }

    }

    fun errorRegister(view: EditText){
        view.requestFocus()
        view.background = resources.getDrawable(R.drawable.share_retangle_error)
    }

    fun errorToast(msg:String){
        FancyToast.makeText(requireContext(),msg,FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show()
    }

}