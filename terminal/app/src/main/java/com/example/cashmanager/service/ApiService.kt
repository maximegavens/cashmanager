package com.example.cashmanager.service

import com.example.cashmanager.model.Product
import com.example.cashmanager.model.User
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Multipart
    @POST("auth/sign_in")
    fun login(@Part("login") login: RequestBody, @Part("password") password: RequestBody): Call<User>


    @GET("market")
    fun getAllProduct(): Call<MutableList<Product?>>

    @GET("market/total")
    fun getTotal(): Call<Any>


    @POST("stock")
    fun createProduct(@Body body: Product): Call<MutableList<Product?>>

    @DELETE("stock/{id}")
    fun deleteProduct(@Path("id") id: Long): Call<MutableList<Product?>>


    @Multipart
    @POST("payment")
    fun sendPayment(@Part("account") account: RequestBody, @Part("amount") amount: RequestBody): Call<Any>
}