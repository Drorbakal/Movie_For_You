package com.example.myapplication.Repository.LoginAndRegister

import android.util.Patterns
import androidx.lifecycle.*
import com.example.myapplication.Repository.FireBase.Auth
import kotlinx.coroutines.launch

class RegisterModel(private val repository: Auth) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _registrationSuccess = MutableLiveData<Boolean>()
    val registrationSuccess: LiveData<Boolean> = _registrationSuccess

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun createUser(userName: String, userEmail: String, password: String) {
        if (userEmail.isEmpty() || userName.isEmpty() || password.isEmpty()) {
            _error.value = "Empty String"
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            _error.value = "Not A Valid Email"
            return
        }

        _isLoading.value = true

        viewModelScope.launch {
            val registrationResult = repository.creatuser(userEmail, password, userName)
            if (registrationResult.isSuccess) {
                _registrationSuccess.value = true
            } else {
                _error.value = registrationResult.exceptionOrNull()?.message ?: "Registration failed"
            }
            _isLoading.value = false
        }
    }

    class RegisterViewModelFactory(private val repo: Auth) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return RegisterModel(repo) as T
        }
    }
}
