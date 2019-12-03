package com.example.cashmanager.service

import androidx.lifecycle.MutableLiveData
import com.example.cashmanager.`object`.RetrofitClient.retrofit
import com.google.gson.internal.LinkedTreeMap
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

        retrofit!!.sendPayment(account, amount).enqueue(object: Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                println("Access server OK")
                println(response.body())
                val resp = response.body() as LinkedTreeMap<String, String>
                data.value = resp.getValue("message")
            }
            override fun onFailure(call: Call<Any>, t: Throwable) {
                data.value = null
                println(t.message)
            }
        })

        return data
    }

    fun getTotal(): MutableLiveData<String> {
        val data = MutableLiveData<String>()

        retrofit!!.getTotal().enqueue(object: Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                println("Access server OK")
                val resp = response.body() as LinkedTreeMap<String, String>
                data.value = resp.getValue("total")
            }
            override fun onFailure(call: Call<Any>, t:Throwable) {
                data.value = null
                println(t.message)
            }
        })

        return data
    }
}