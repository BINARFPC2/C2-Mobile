package com.dwiki.tiketku.ResetPassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dwiki.tiketku.R
import com.dwiki.tiketku.databinding.FragmentResetPasswordBinding
import com.dwiki.tiketku.util.Status
import com.dwiki.tiketku.viewmodel.RegisterViewModel
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import org.json.JSONTokener


@AndroidEntryPoint
class ResetPasswordFragment : Fragment() {

    private lateinit var binding: FragmentResetPasswordBinding
    private val registViewModel: RegisterViewModel by viewModels()

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
        resetPassword()
    }

    private fun resetPassword() {
        binding.apply {
            btnSimpan.setOnClickListener {
                val newPass = etPassword.text.toString()
                val confirmPass = etPassword.text.toString()

                if (etPassword.length() < 8 || etkonfirmPassword.length() < 8){
                    errorToast("Password min 8 karakter!")
                } else {
                    registViewModel.resetPassword(newPass,confirmPass).observe(viewLifecycleOwner){
                        when(it.status){
                            Status.SUCCESS -> {
                                FancyToast.makeText(requireContext(),"Berhasil Ganti Password, Silahkan login",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show()
                                findNavController().navigate(R.id.action_resetPasswordFragment_to_loginFragment)
                            }
                            Status.ERROR -> {
                                val jsonObject = JSONTokener(it.message).nextValue() as JSONObject
                                val msg = jsonObject.getString("message")
                                when(msg){
                                    "Password not match" -> {
                                        errorRegister(etkonfirmPassword)
                                        errorToast("Password tidak Cocok")
                                    }
                                }
                            }
                            Status.LOADING ->{}
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
        FancyToast.makeText(requireContext(),msg, FancyToast.LENGTH_SHORT, FancyToast.ERROR,false).show()
    }

}