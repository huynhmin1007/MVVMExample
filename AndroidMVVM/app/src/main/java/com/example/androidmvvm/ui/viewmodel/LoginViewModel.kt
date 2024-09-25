package com.example.androidmvvm.ui.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmvvm.R
import com.example.androidmvvm.data.model.User
import com.example.androidmvvm.data.repository.UserRepository
import com.example.androidmvvm.ui.data.Resource
import com.example.androidmvvm.ui.view.usecase.login.LoginFormState
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<Resource<User>>()
    val loginResult: LiveData<Resource<User>> = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = Resource.Loading
            val resource = userRepository.login(email, password)
            _loginResult.value = when (resource) {
                is Resource.Success -> Resource.Success(resource.data)
                is Resource.Failure -> Resource.Failure(resource.exception)
                else -> Resource.Failure(Exception("Unknown Error"))
            }
        }
    }

    fun loginDataChanged(email: String, password: String) {
        val emailError = if (!isEmailValid(email)) {
            R.string.invalid_email
        } else null

        val passwordError = if (password.isNotBlank() && !isPasswordValid(password)) {
            R.string.invalid_password
        } else null

        _loginForm.value = LoginFormState(
            emailError = emailError,
            passwordError = passwordError,
            isDataValid = emailError == null && passwordError == null && password.isNotBlank()
        )
    }

    private fun isEmailValid(email: String): Boolean {
        return if (email.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            email.isNotBlank()
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}