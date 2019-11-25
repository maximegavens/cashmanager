package com.example.cashmanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cashmanager.model.Product
import com.example.cashmanager.service.ProductService

class BillViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var products: MutableLiveData<MutableList<Product?>>

    fun getAllProduct(): LiveData<MutableList<Product?>> {
        if(!::products.isInitialized) {
            products = MutableLiveData()
            products.value = mutableListOf<Product?>()
        }
        getAll()
        return products
    }

    private fun getAll() {
        val service = ProductService()
        products = service.getAllProduct()
    }
}