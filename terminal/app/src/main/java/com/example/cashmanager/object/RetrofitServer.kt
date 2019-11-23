package com.example.cashmanager.`object`

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.cashmanager.service.ApiService
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


object RetrofitServer {

    private val okHttpClient = OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).build()

    var BASE_URL = "None"

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        retrofit.create(ApiService::class.java)
    }
}