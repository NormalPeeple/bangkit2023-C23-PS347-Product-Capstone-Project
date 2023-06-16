package com.example.storyvan.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyvan.model.LoginModel
import com.example.storyvan.model.UserRepository
import com.example.storyvan.response.AddNewStoryResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel (private val userRepository: UserRepository) : ViewModel() {
    val uploadResponse: LiveData<AddNewStoryResponse> = userRepository.uploadResponse

    fun uploadStory(token: String, file: MultipartBody.Part, description: RequestBody) {
        viewModelScope.launch {
            userRepository.uploadStory(token, file, description)
        }
    }

    fun getSession(): LiveData<LoginModel> {
        return userRepository.getToken()
    }
}