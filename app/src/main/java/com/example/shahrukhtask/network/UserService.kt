package com.example.shahrukhtask.network

import com.example.shahrukhtask.model.UserResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap


interface UserService {
    @GET("users")
    suspend fun getUsers(@QueryMap params: HashMap<String?, String?>): Response<UserResponse>
}

object RetrofitInstance {
    val api : UserService by lazy {
        Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserService::class.java)
    }
}