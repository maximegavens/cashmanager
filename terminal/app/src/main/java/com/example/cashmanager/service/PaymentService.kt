package com.example.cashmanager.service

import androidx.lifecycle.MutableLiveData
import com.example.cashmanager.`object`.RetrofitServer
import com.example.cashmanager.model.Product
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentService {

    fun sendPayment(userAccount: String, billAmount: String): MutableLiveData<String> {
        val data = MutableLiveData<String>()
        val account = RequestBody.create(MediaType.parse("text/plain"), userAccount)
        val amount = RequestBody.create(MediaType.parse("text/plain"), billAmount)

        RetrofitServer.instance.sendPayment(account, amount).enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                println("Access server OK")
                data.value = response.body()
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                println(t.message)
            }
        })

        return data
    }
}