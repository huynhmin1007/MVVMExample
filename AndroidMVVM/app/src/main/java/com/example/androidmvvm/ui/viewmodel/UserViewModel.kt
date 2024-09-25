package com.example.androidmvvm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmvvm.data.model.User
import com.example.androidmvvm.data.repository.UserRepository
import com.example.androidmvvm.ui.data.Resource
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _userList = MutableLiveData<Resource<List<User>>>()
    val userList: LiveData<Resource<List<User>>> = _userList

    private val _user = MutableLiveData<Resource<User>>()
    val user: LiveData<Resource<User>> = _user

    fun fetchUserList(
        page: Int = 0,
        limit: Int = 10,
        orderBy: String = "id",
        orderDir: String = "asc"
    ) {
        viewModelScope.launch {
            _userList.value = Resource.Loading
            val resource = userRepository.findAll(page, limit, orderBy, orderDir)
            _userList.value = when (resource) {
                is Resource.Success -> Resource.Success(resource.data)
                is Resource.Failure -> Resource.Failure(resource.exception)
                else -> Resource.Failure(Exception("Unknown Error"))
            }
        }
    }

    fun updateUser(resource: Resource<User>) {
        _user.postValue(resource)
    }
}