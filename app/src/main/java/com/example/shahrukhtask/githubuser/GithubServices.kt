package com.example.shahrukhtask.githubuser

import com.example.shahrukhtask.model.User
import com.example.shahrukhtask.network.NetworkResult
import com.google.gson.Gson
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface GithubServices {
    @GET("users")
    suspend fun getUsers():Response<List<GithubUser>>

    object GithubRetrofit{
        val githubServices by lazy {
            Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubServices::class.java)
        }
    }
}