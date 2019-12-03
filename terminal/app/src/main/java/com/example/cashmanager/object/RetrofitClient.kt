package com.example.cashmanager.`object`

import com.example.cashmanager.service.ApiService
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    var timeOut: Long = 10
    var okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(timeOut, TimeUnit.SECONDS)
        .build()

    var BASE_URL: String = "http://192.168.43.209:8080"

    var retrofit: ApiService? = null

    fun constructApiService(ip: String) {
        BASE_URL = "http://".plus(ip).plus(":8080")
        retrofit = retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }
}