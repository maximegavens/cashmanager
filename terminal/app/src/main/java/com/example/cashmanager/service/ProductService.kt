package com.example.cashmanager.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cashmanager.`object`.RetrofitServer
import com.example.cashmanager.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductService {

    fun getAllProduct(): MutableLiveData<MutableList<Product?>> {
        val data = MutableLiveData<MutableList<Product?>>()

        RetrofitServer.instance.getAllProduct().enqueue(object: Callback<MutableList<Product?>> {
            override fun onResponse(call: Call<MutableList<Product?>>, response: Response<MutableList<Product?>>) {
                println("Access server OK")
                data.value = response.body()
            }
            override fun onFailure(call: Call<MutableList<Product?>>, t: Throwable) {
                println(t.message)
            }
        })

        return data
    }

    fun createProduct(product: Product): LiveData<Product> {
        val data = MutableLiveData<Product>()

        RetrofitServer.instance.createProduct(product).enqueue(object: Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                println("Access server OK")
                data.value = response.body()
            }
            override fun onFailure(call: Call<Product>, t: Throwable) {
                println(t.message)
            }
        })

        return data
    }

    fun updateProduct(id: Long, product: Product): LiveData<Product> {
        val data = MutableLiveData<Product>()

        RetrofitServer.instance.updateProduct(id, product).enqueue(object: Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                println("Access server OK")
                data.value = response.body()
            }
            override fun onFailure(call: Call<Product>, t:Throwable) {
                println(t.message)
            }
        })

        return data
    }

    fun deleteProduct(id: Long): LiveData<Product> {
        val data = MutableLiveData<Product>()

        RetrofitServer.instance.deleteProduct(id).enqueue(object: Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                println("Access server OK")
                data.value = response.body()
            }
            override fun onFailure(call: Call<Product>, t:Throwable) {
                println(t.message)
            }
        })

        return data
    }
}