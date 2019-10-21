package com.example.cashmanager

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.util.*
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private val AUTH = "Basic" + Base64.encodeToString("gavens:123456".toByteArray(), Base64.NO_WRAP)

/*
    private const val BASE_URL = "http://10.0.2.2:8080" // emulator API 29
*/
/*
    private const val BASE_URL = "http://192.168.1.15:8080" // ip house
*/
    private const val BASE_URL = "http://172.20.0.1:8080" // ip epitech

    private val okHttpClient = OkHttpClient.Builder().callTimeout(15, TimeUnit.SECONDS).build()

/*    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor{ chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .addHeader("Authorization", AUTH)
                .method(original.method(), original.body())

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()*/

    val instance: JsonPlaceHolderApi by lazy {


        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
/*            .client(okHttpClient)*/
            .build()
        retrofit.create(JsonPlaceHolderApi::class.java)
    }
}