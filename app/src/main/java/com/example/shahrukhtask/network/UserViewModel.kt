package com.example.shahrukhtask.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shahrukhtask.model.User
import com.example.shahrukhtask.model.UserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _usersResponse = MutableLiveData<NetworkResult<UserResponse>>()
    val usersResponse :LiveData<NetworkResult<UserResponse>>
        get() = _usersResponse

    val isInternetConnected  = MutableLiveData<Boolean>()



    fun getUsers(page: String,perPage:String) {
        uiScope.launch {
            val params: HashMap<String?, String?> = HashMap()
            params.put("page", page)
            params.put("per_page", perPage)
            val response =
                userRepository.getUser(params)
            response.data?.data?.let { userRepository.insertUsers(it) }
            Log.d("TAG", "getUsers:${response.data?.data} ")
            _usersResponse.postValue(response)
        }
    }

    fun insertUserData(){
        uiScope.launch {
            usersResponse.value?.data?.data.let {
                userRepository.insertUsers(it!!)
            }
        }
    }
    private val _databaseUserList = MutableLiveData<List<User>>()
    val databaseUserList: LiveData<List<User>>
        get() = _databaseUserList

    fun getAllUsers(){
        uiScope.launch {
            _databaseUserList.value = userRepository.getAllUser()
        }
    }
}