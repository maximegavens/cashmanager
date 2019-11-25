package com.example.cashmanager.service

import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Response
import androidx.lifecycle.LiveData
import com.example.cashmanager.`object`.RetrofitServer
import com.example.cashmanager.model.User
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Callback
import kotlin.coroutines.coroutineContext

class LoginService {

    fun getUserByIdAndPassword(userLogin: String, userPassword: String): LiveData<User> {
        val data = MutableLiveData<User>()
        val login = RequestBody.create(MediaType.parse("text/plain"), userLogin)
        val password = RequestBody.create(MediaType.parse("text/plain"), userPassword)

        RetrofitServer.instance.login(login, password).enqueue(object: Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                println("Access server OK")
                println(response.code())
                println(response.body())
                data.value = response.body()
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                println(t.message)
                data.value = null
            }
        })

        return data
    }
}