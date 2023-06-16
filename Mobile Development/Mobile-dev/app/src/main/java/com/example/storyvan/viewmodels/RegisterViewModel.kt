package com.example.storyvan.viewmodels

import androidx.lifecycle.ViewModel
import com.example.storyvan.model.UserRepository

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun register(penyelenggara: String, agama: String, kota_asal_penyelenggara: String, email: String, password: String, no_hp: String) =
        userRepository.registerUser(penyelenggara, agama, kota_asal_penyelenggara, email, password, no_hp)

}