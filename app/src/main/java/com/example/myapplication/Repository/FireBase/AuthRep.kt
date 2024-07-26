package com.example.myapplication.Repository.FireBase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Repository.LoginAndRegister.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRep : Auth {

    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val userRef = FirebaseFirestore.getInstance().collection("users")

    private val _connect = MutableLiveData(0)
    val connect: LiveData<Int> get() = _connect

    init {
        if (firebaseAuth.currentUser != null) {
            _connect.value = 1
        }
    }

    override suspend fun currentuser(): Result<User> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val user = userRef.document(firebaseAuth.currentUser!!.uid).get().await()
                    .toObject(User::class.java)
                if (user != null) {
                    _connect.postValue(1)
                } else {
                    _connect.postValue(0)
                }
                Result.success(user!!)
            }
        }
    }

    override suspend fun login(email: String, password: String): Result<User> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                val user = userRef.document(result.user?.uid!!).get().await().toObject(User::class.java)
                _connect.postValue(1)
                Result.success(user!!)
            }
        }
    }

    override suspend fun creatuser(useremail: String, userpassword: String, username: String): Result<User> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val registrationResult = firebaseAuth.createUserWithEmailAndPassword(useremail, userpassword).await()
                val userId = registrationResult.user?.uid!!
                val newUser = User(username, useremail)
                userRef.document(userId).set(newUser).await()
                _connect.postValue(1)
                Result.success(newUser)
            }
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
        _connect.postValue(0)
    }

    private suspend fun <T> safeCall(action: suspend () -> Result<T>): Result<T> {
        return try {
            action()
        } catch (e: Exception) {
            _connect.postValue(0)
            Result.failure(e)
        }
    }
}
