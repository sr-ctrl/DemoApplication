package com.example.shahrukhtask.network

import com.example.shahrukhtask.model.User
import com.example.shahrukhtask.model.UserResponse
import com.example.shahrukhtask.room.UserDao


class UserRepository(private val userDao: UserDao) {


   suspend fun getAllUser():List<User>{
      return userDao.getAllUsers()
   }

   suspend fun insertUsers(user: List<User>) {
      return userDao.insertUsers(user)
   }

   suspend fun getUser(params: HashMap<String?, String?> = HashMap()
   ): NetworkResult<UserResponse> {

      val response = RetrofitInstance.api.getUsers(params)
      return if (response.isSuccessful){
         val responseBody = response.body()
         if (responseBody != null){
            NetworkResult.Success(responseBody)
         }else{
            NetworkResult.Error(response.message())
         }
      }else{
         NetworkResult.Error(response.errorBody().toString())
      }
   }
}

sealed class NetworkResult<T>(val data: T? = null, val message: String? = null ) {
   class Success<T>(data: T?,message: String? = null) : NetworkResult<T>(data)
   class Error<T>(message: String?, data: T? = null) : NetworkResult<T>(data,message)
}