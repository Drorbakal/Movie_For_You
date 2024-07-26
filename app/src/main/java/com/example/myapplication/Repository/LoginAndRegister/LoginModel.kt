package com.example.myapplication.Repository.LoginAndRegister

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Repository.FireBase.AuthRep
import kotlinx.coroutines.launch

class LoginModel(private val repository: AuthRep) : ViewModel() {
    private val _userSignInStatus = MutableLiveData<Boolean>()
    val userSignInStatus: LiveData<Boolean> = _userSignInStatus

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    init {
        viewModelScope.launch {
            _currentUser.value = repository.currentuser()?.getOrNull()
        }
    }

    fun signInUser(useremail: String, userPassword: String) {
        if (useremail.isEmpty() || userPassword.isEmpty()) {
            _userSignInStatus.value = false
        } else {
            viewModelScope.launch {
                val loginResult = repository.login(useremail, userPassword)
                _userSignInStatus.value = loginResult.isSuccess
                if (loginResult.isSuccess) {
                    _currentUser.value = loginResult.getOrNull()
                }
            }
        }
    }

    class LoginViewModelFactory(private val repo: AuthRep) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginModel::class.java)) {
                return LoginModel(repo) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
