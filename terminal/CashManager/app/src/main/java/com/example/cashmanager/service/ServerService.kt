package com.example.cashmanager.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cashmanager.model.Server
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServerService {
    fun getServerById(serverId: String, password: String): LiveData<Server> {
        val data = MutableLiveData<Server>()

        RetrofitClient.instance.login(serverId, password).enqueue(object: Callback<Server>{
            override fun onResponse(call: Call<Server>, response: Response<Server>) {
                println("post success")
                data.setValue(response.body())
            }
            override fun onFailure(call: Call<Server>, t: Throwable) {
                println(t.message)
            }
        })

        return data
    }
}