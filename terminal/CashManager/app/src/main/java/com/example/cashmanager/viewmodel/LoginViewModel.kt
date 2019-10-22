package com.example.cashmanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cashmanager.model.Server
import com.example.cashmanager.model.User
import com.example.cashmanager.service.ServerService
import com.example.cashmanager.service.UserService

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var users: LiveData<List<User>>
    private lateinit var server: LiveData<Server>

    fun getUsers(): LiveData<List<User>> {
        if (!::users.isInitialized) {
            users = MutableLiveData()
            loadUsers()
        }
        return users
    }

    fun getServer(id: String, password: String): LiveData<Server> {
        if (!::server.isInitialized) {
            server = MutableLiveData()
            loadServer(id, password)
        }
        return server
    }

    private fun loadUsers() {
        val userService = UserService()
        users = userService.getProjectList()
    }

    private fun loadServer(id: String, password: String) {
        val repo = ServerService()
        server = repo.getServerById(id, password)
    }
}