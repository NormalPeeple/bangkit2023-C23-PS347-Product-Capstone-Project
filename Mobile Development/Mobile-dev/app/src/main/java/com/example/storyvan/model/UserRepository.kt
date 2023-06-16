package com.example.storyvan.model

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.paging.*
import com.example.storyvan.helper.ResultState
import com.example.storyvan.helper.StoryPagingSource
import com.example.storyvan.helper.UserPreferences
import com.example.storyvan.response.*
import com.example.storyvan.retrofit.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
){
    private val registerResult = MediatorLiveData<ResultState<RegisterResponse>>()
    private val loginResult = MediatorLiveData<ResultState<LoginResponse>>()

    private val _uploadResponse = MutableLiveData<AddNewStoryResponse>()
    val uploadResponse: LiveData<AddNewStoryResponse> = _uploadResponse

    fun registerUser(penyelenggara: String, agama: String, kota_asal_penyelenggara: String, email: String, password: String,  no_hp: String): LiveData<ResultState<RegisterResponse>> {
        registerResult.value = ResultState.Loading
        val client = apiService.register(penyelenggara, agama, kota_asal_penyelenggara, email, password, no_hp)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    val registerStatus = response.body()
                    if (registerStatus != null) {
                        registerResult.value = ResultState.Success(registerStatus)
                    } else {
                        registerResult.value = ResultState.Error(error_register)
                    }
                } else {
                    registerResult.value = ResultState.Error(error_register)
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                registerResult.value = ResultState.Error(error_register)
            }

        })

        return registerResult
    }

    fun loginUser(email: String, password: String): LiveData<ResultState<LoginResponse>> {
        loginResult.value = ResultState.Loading
        val client = apiService.login(
            email,
            password,
        )

        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginInfo = response.body()
                    if (loginInfo != null) {
                        loginResult.value = ResultState.Success(loginInfo)
                    } else {
                        loginResult.value = ResultState.Error(error_login)
                    }
                } else {
                    loginResult.value = ResultState.Error(error_login)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginResult.value = ResultState.Error(error_login)
            }
        })

        return loginResult
    }

    fun getStory(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(userPreferences, apiService)
            }
        ).liveData
    }

    fun uploadStory(token: String, file: MultipartBody.Part, description: RequestBody) {
        val client = apiService.postStory(file, description)

        client.enqueue(object : Callback<AddNewStoryResponse> {
            override fun onResponse(
                call: Call<AddNewStoryResponse>,
                response: Response<AddNewStoryResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    _uploadResponse.value = response.body()
                } else {
                    Log.e(
                        TAG,
                        "onFailure: ${response.message()}, ${response.body()?.message.toString()}"
                    )
                }
            }

            override fun onFailure(call: Call<AddNewStoryResponse>, t: Throwable) {
                Log.d("error upload", t.message.toString())
            }
        }
        )
    }

    suspend fun saveToken(token: LoginModel) {
        userPreferences.saveToken(token)
    }

    fun getToken(): LiveData<LoginModel> {
        return userPreferences.getToken().asLiveData()
    }

    suspend fun login() {
        userPreferences.login()
    }

    suspend fun deleteToken() {
        userPreferences.deleteToken()
    }

    companion object{
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(apiService: ApiService, userPreferences: UserPreferences) =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService,userPreferences)
            }.also { instance = it }

        private const val error_register = "Register Failed!, Pastikan semua data sudah benar"
        private const val error_login = "Login Failed, Make sure the e-mail & password is correct!"
    }
}