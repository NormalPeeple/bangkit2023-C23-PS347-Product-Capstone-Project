package com.example.storyvan.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.storyvan.databinding.ActivityLoginBinding
import com.example.storyvan.helper.ResultState
import com.example.storyvan.helper.ViewModelFactory
import com.example.storyvan.model.LoginModel
import com.example.storyvan.viewmodels.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private val loginViewModel: LoginViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModelFactory = ViewModelFactory.getInstance(this)
        supportActionBar?.hide()

        buttonRegister()
        actionLogin()
        animation()

    }

    private fun buttonRegister() {
        binding.buttonIntentReg.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun actionLogin(){
        binding.buttonLogin.setOnClickListener {
            val email = binding.emailed.text.toString()
            val password = binding.password.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                val result = loginViewModel.login(email, password)
                result.observe(this) {
                    when (it) {
                        is ResultState.Loading -> {
                            binding.progressBar2.visibility = View.VISIBLE
                        }
                        is ResultState.Error -> {
                            binding.progressBar2.visibility = View.INVISIBLE
                            val error = it.error
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                        }
                        is ResultState.Success -> {
                            binding.progressBar2.visibility = View.INVISIBLE
                            val data = it.data
//                            loginViewModel.saveToken(
//                                LoginModel(
//                                    data.loginResult.name,
//                                AUTH_KEY + (data.loginResult.token),
//                                true
//                            )
//                            )
                            loginViewModel.login()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun animation(){
        ObjectAnimator.ofFloat(binding.textJudul, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val login = ObjectAnimator.ofFloat(binding.buttonLogin, View.ALPHA, 1f).setDuration(500)
        val regis = ObjectAnimator.ofFloat(binding.buttonIntentReg, View.ALPHA, 1f).setDuration(500)
        val eml = ObjectAnimator.ofFloat(binding.emailed, View.ALPHA, 1f).setDuration(500)
        val pss = ObjectAnimator.ofFloat(binding.password, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(eml, pss, login, regis)
            start()
        }


    }

    companion object {
        private const val AUTH_KEY = "Bearer "
    }

}