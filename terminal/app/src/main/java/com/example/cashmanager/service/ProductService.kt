package com.example.cashmanager.service

import androidx.lifecycle.MutableLiveData
import com.example.cashmanager.`object`.RetrofitClient.retrofit
import com.example.cashmanager.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductService {

    fun getAllProduct(): MutableLiveData<MutableList<Product?>> {
        val data = MutableLiveData<MutableList<Product?>>()

        retrofit!!.getAllProduct().enqueue(object: Callback<MutableList<Product?>> {
            override fun onResponse(call: Call<MutableList<Product?>>, response: Response<MutableList<Product?>>) {
                println("Access server OK")
                data.value = response.body()
            }
            override fun onFailure(call: Call<MutableList<Product?>>, t: Throwable) {
                data.value = null
                println(t.message)
            }
        })

        return data
    }

    fun createProduct(product: Product): MutableLiveData<MutableList<Product?>> {
        val data = MutableLiveData<MutableList<Product?>>()

        retrofit!!.createProduct(product).enqueue(object: Callback<MutableList<Product?>> {
            override fun onResponse(call: Call<MutableList<Product?>>, response: Response<MutableList<Product?>>) {
                println("Access server OK")
                data.value = response.body()
            }
            override fun onFailure(call: Call<MutableList<Product?>>, t: Throwable) {
                data.value = null
                println(t.message)
            }
        })

        return data
    }

    fun deleteProduct(id: Long): MutableLiveData<MutableList<Product?>> {
        val data = MutableLiveData<MutableList<Product?>>()

        retrofit!!.deleteProduct(id).enqueue(object: Callback<MutableList<Product?>> {
            override fun onResponse(call: Call<MutableList<Product?>>, response: Response<MutableList<Product?>>) {
                println("Access server OK")
                data.value = response.body()
            }
            override fun onFailure(call: Call<MutableList<Product?>>, t:Throwable) {
                data.value = null
                println(t.message)
            }
        })

        return data
    }

    fun deleteAllProduct(): MutableLiveData<MutableList<Product?>> {
        val data = MutableLiveData<MutableList<Product?>>()

        retrofit!!.deleteAllProduct().enqueue(object: Callback<MutableList<Product?>> {
            override fun onResponse(call: Call<MutableList<Product?>>, response: Response<MutableList<Product?>>) {
                println("Access server OK")
                data.value = response.body()
            }
            override fun onFailure(call: Call<MutableList<Product?>>, t:Throwable) {
                data.value = null
                println(t.message)
            }
        })

        return data
    }
}