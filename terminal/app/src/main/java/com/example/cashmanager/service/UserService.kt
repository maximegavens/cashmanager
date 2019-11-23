package com.example.cashmanager.service

import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Response
import androidx.lifecycle.LiveData
import com.example.cashmanager.`object`.RetrofitClient
import com.example.cashmanager.`object`.RetrofitServer
import com.example.cashmanager.model.User
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Callback
import retrofit2.Retrofit

class UserService(ipServer: String) {

    private val url = "http://".plus(ipServer).plus(":8080")
    private val retrofitServer = RetrofitServer

    init {
        retrofitServer.BASE_URL = url
    }

    fun getUserByIdAndPassword(userLogin: String, userPassword: String): LiveData<User> {
        val data = MutableLiveData<User>()
        val login = RequestBody.create(MediaType.parse("text/plain"), userLogin)
        val password = RequestBody.create(MediaType.parse("text/plain"), userPassword)

        retrofitServer.instance.login(login, password).enqueue(object: Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                println("Access server OK")
                println(response.code())
                println(response.body())
                data.setValue(response.body())
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                println(t.message)
            }
        })

        return data
    }
}