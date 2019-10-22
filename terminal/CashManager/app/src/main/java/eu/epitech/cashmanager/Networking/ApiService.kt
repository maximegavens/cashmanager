package eu.epitech.cashmanager.Networking

import com.google.gson.GsonBuilder
import eu.epitech.cashmanager.Models.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>

    companion object Factory {
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build();

            return retrofit.create(ApiService::class.java);
        }
    }
}