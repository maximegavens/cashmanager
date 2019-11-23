package com.example.cashmanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cashmanager.model.Product
import com.example.cashmanager.service.ProductService

class BillViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var products: MutableLiveData<MutableList<Product?>>

    fun getAllProduct(ipServer: String): LiveData<MutableList<Product?>> {
        if(!::products.isInitialized) {
            products = MutableLiveData()
            products.value = mutableListOf<Product?>()
        }
        getAll(ipServer)
        return products
    }

    private fun getAll(ipServer: String) {
        val service = ProductService(ipServer)
        products = service.getAllProduct()
    }
}