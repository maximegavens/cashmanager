package com.example.cashmanager.`object`

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.cashmanager.service.ApiService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


object RetrofitServer {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .build()


    /**
     * Replace your BASE_URL by your local ip server
     */
    /*private const val BASE_URL = "http://192.168.1.15:8080"*/
    private val BASE_URL = "http://192.168.43.209:8080"

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        retrofit.create(ApiService::class.java)
    }
}