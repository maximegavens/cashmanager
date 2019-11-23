package com.example.cashmanager.`object`

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.util.*
import com.example.cashmanager.service.ApiService
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


object RetrofitClient {

    private val AUTH = "Basic" + Base64.encodeToString("gavens:123456".toByteArray(), Base64.NO_WRAP)

    private const val BASE_URL = "http://192.168.1.15:8080" // ip house

    /*private const val BASE_URL = "http://192.168.43.209:8080" // ip phone*/

    private val okHttpClient = OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).build()

/*    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor{ chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .addHeader("Authorization", AUTH)
                .method(original.method(), original.body())

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()*/

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        retrofit.create(ApiService::class.java)
    }

    /*@Throws(IOException::class)
    private fun onOnIntercept(chain: Interceptor.Chain): Response {
        try {
            val response = chain.proceed(chain.request())
            val content = response.toString()
            val body = response.body().toString()
            Log.d(TAG,  content.plus(" - body: ").plus(body))
            return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), content)).build()
        } catch (exception: SocketTimeoutException) {
            exception.printStackTrace()
            if (listener != null)
                listener.onConnectionTimeout()
        }

        return chain.proceed(chain.request())
    }*/
}