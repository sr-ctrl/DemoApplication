package com.example.shahrukhtask.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shahrukhtask.githubuser.GithubRepository
import com.example.shahrukhtask.githubuser.GithubUserViewModel

class UserViewModelFactory(
    private  val repository: UserRepository,
): ViewModelProvider.Factory{
    @Suppress("Unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       if(modelClass.isAssignableFrom(UserViewModel::class.java)) {
           return UserViewModel(repository) as T
       }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}

class GithubFactory(
    private  val repository: GithubRepository,
): ViewModelProvider.Factory{
    @Suppress("Unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GithubUserViewModel::class.java)) {
            return GithubUserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}