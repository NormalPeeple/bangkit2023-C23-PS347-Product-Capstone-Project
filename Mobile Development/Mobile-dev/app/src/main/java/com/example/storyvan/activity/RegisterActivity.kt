package com.example.storyvan.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.storyvan.databinding.ActivityRegisterBinding
import com.example.storyvan.helper.ResultState
import com.example.storyvan.helper.ViewModelFactory
import com.example.storyvan.viewmodels.RegisterViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private val registerViewModel:RegisterViewModel by viewModels{viewModelFactory}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelFactory = ViewModelFactory.getInstance(this)

        actionRegister()
    }

    private fun actionRegister(){
        binding.buttonRegister.setOnClickListener {
            val penyelenggara = binding.edName.text.toString()
            val agama = binding.edAgama.text.toString()
            val kota_asal_penyelenggara = binding.edKota.text.toString()
            val email = binding.emailed.text.toString()
            val password = binding.passwordreg.text.toString()
            val no_hp = binding.edNoTelp.text.toString()
            if (penyelenggara.isNotEmpty() && agama.isNotEmpty() && kota_asal_penyelenggara.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && no_hp.isNotEmpty()){
                val result = registerViewModel.register(penyelenggara, agama, kota_asal_penyelenggara, email, password, no_hp)
                result.observe(this){
                    when(it){
                        is ResultState.Error -> {
                            binding.progressBar.visibility = View.INVISIBLE
                            val error = it.error
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                        }
                        is ResultState.Success -> {
                            binding.progressBar.visibility = View.INVISIBLE
                            Toast.makeText(this, "register_success", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        }
                        is ResultState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

}