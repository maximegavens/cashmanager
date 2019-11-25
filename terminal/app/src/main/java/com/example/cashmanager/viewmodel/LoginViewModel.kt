package com.example.cashmanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cashmanager.model.User
import com.example.cashmanager.service.LoginService

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var user: LiveData<User>

    fun getUser(login: String, password: String): LiveData<User> {
        if (!::user.isInitialized) {
            user = MutableLiveData()
        }
        loadUser(login, password)
        return user
    }

    private fun loadUser(id: String, password: String) {
        val repo = LoginService()
        user = repo.getUserByIdAndPassword(id, password)
    }
}