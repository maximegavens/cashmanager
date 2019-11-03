package com.example.cashmanager.service

import com.example.cashmanager.model.Server
import com.example.cashmanager.model.User
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("users")
    fun getUsers(): Call<List<User>>

    @Multipart
    @POST("server/sign_in")
    fun login(@Part("id_server") id: RequestBody, @Part("password") password: RequestBody): Call<Server>
}