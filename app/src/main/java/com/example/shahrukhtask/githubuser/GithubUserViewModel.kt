package com.example.shahrukhtask.githubuser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shahrukhtask.network.NetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GithubUserViewModel(val repository: GithubRepository):ViewModel() {

    val viewModelScope = CoroutineScope(Dispatchers.Main + Job())

    var userlist = MutableLiveData<NetworkResult<List<GithubUser>>>()

    fun getUsers(){
        viewModelScope.launch {
            val response = repository.getUser()
            userlist.value = response
        }

    }
}