package com.example.myapplication.Repository.FireBase

import com.example.myapplication.Repository.LoginAndRegister.User

interface Auth {
    suspend fun currentuser(): Result<User>
    suspend fun login(email: String, password: String): Result<User>
    suspend fun creatuser(useremail: String, userpassword: String, username: String): Result<User>
    fun logout()
}
