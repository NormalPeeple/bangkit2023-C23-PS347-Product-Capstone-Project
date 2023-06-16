package com.example.storyvan.retrofit

import com.example.storyvan.response.AddNewStoryResponse
import com.example.storyvan.response.GetAllStoryResponse
import com.example.storyvan.response.LoginResponse
import com.example.storyvan.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("pengguna")
    fun register(
        @Field("penyelenggara") penyelenggara: String,
        @Field("agama") agama: String,
        @Field("kota_asal_penyelenggara") kota_asal_penyelenggara: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("no_hp") no_hp : String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("event")
    suspend fun getStory(
//        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<GetAllStoryResponse>

    @Multipart
    @POST("event")
    fun postStory(
//        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<AddNewStoryResponse>
}