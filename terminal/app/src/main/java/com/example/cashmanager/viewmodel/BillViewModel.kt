package com.example.cashmanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cashmanager.model.Product
import com.example.cashmanager.service.PaymentService
import com.example.cashmanager.service.ProductService

class BillViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var products: MutableLiveData<MutableList<Product?>>
    private lateinit var total: MutableLiveData<String>

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

    fun getTotal(): LiveData<String> {
        if(!::total.isInitialized) {
            total = MutableLiveData()
            total.value = String()
        }
        recoverTotal()
        return total
    }

    private fun recoverTotal() {
        val service = PaymentService()
        total = service.getTotal()
    }
}