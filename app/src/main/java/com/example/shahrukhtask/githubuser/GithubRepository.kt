package com.example.shahrukhtask.githubuser

import com.example.shahrukhtask.network.NetworkResult

class GithubRepository() {

    suspend fun getUser():NetworkResult<List<GithubUser>>{
        val response = GithubServices.GithubRetrofit.githubServices.getUsers()
       return if (response.isSuccessful){
           val responseBody = response.body()
           if (responseBody != null){
               NetworkResult.Success(responseBody,null)
           }else{
               NetworkResult.Error(response.errorBody().toString(),null)
           }
       }else{
           NetworkResult.Error(response.errorBody().toString(),null)
       }
    }
}