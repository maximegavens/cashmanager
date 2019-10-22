package com.example.cashmanager.service

import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Response
import androidx.lifecycle.LiveData
import com.example.cashmanager.model.User
import retrofit2.Callback

class UserService {
    fun getProjectList(): LiveData<List<User>> {
        val data = MutableLiveData<List<User>>()

        RetrofitClient.instance.getUsers().enqueue(object: Callback<List<User>>{
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                println("success")
                val userList = response.body()
                userList?.let {
                    for( user in it) {
                        println(user)
                    }
                }
                data.setValue(response.body())
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                println(t.message)
            }
        })

        return data
    }
}