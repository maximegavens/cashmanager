package com.example.cashmanager.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cashmanager.model.Server
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServerService {
    fun getServerByIdAndPassword(serverId: String, serverPassword: String): LiveData<Server> {
        val data = MutableLiveData<Server>()
        val id = RequestBody.create(MediaType.parse("text/plain"), serverId)
        val password = RequestBody.create(MediaType.parse("text/plain"), serverPassword)

        RetrofitClient.instance.login(id, password).enqueue(object: Callback<Server>{
            override fun onResponse(call: Call<Server>, response: Response<Server>) {
                println("post create success")
                println(response.body())
                data.setValue(response.body())
            }
            override fun onFailure(call: Call<Server>, t: Throwable) {
                println(t.message)
            }
        })

        return data
    }
}