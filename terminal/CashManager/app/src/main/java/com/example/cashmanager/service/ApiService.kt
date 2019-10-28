package com.example.cashmanager.service

import com.example.cashmanager.model.Server
import com.example.cashmanager.model.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("users")
    fun getUsers(): Call<List<User>>

    @FormUrlEncoded
    @POST("server/sign_in")
    fun login(@Field("id") id: String,
              @Field("password") password: String): Call<Server>
}