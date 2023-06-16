package com.example.storyvan.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyvan.model.LoginModel
import com.example.storyvan.model.UserRepository
import com.example.storyvan.response.ListStoryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val userRepository: UserRepository
): ViewModel() {

    val getListStory: LiveData<PagingData<ListStoryItem>> =
        userRepository.getStory().cachedIn(viewModelScope)

    fun checkToken(): LiveData<LoginModel> {
        return userRepository.getToken()
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.deleteToken()
        }
    }
}