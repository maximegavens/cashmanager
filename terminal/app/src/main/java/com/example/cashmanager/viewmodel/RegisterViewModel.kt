package com.example.cashmanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cashmanager.model.Product
import com.example.cashmanager.service.PaymentService
import com.example.cashmanager.service.ProductService

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var service: ProductService
    private lateinit var products: MutableLiveData<MutableList<Product?>>
    private lateinit var total: MutableLiveData<String>

    fun getAllProduct(): LiveData<MutableList<Product?>> {
        if(!::products.isInitialized) {
            products = MutableLiveData()
            products.value = mutableListOf<Product?>()
        }
        if(!::service.isInitialized) {
            service = ProductService()
        }
        getAll()
        return products
    }

    private fun getAll() {
        products = service.getAllProduct()
    }

    fun createProduct(p: Product): LiveData<MutableList<Product?>> {
        if(!::products.isInitialized) {
            products = MutableLiveData()
            products.value = mutableListOf<Product?>()
        }
        if(!::service.isInitialized) {
            service = ProductService()
        }
        create(p)
        return products
    }

    private fun create(p: Product) {
        products = service.createProduct(p);
    }

    fun deleteProduct(id: Long): LiveData<MutableList<Product?>> {
        if(!::products.isInitialized) {
            products = MutableLiveData()
        }
        if(!::service.isInitialized) {
            service = ProductService()
        }
        delete(id)
        return products
    }

    private fun delete(id: Long) {
        products = service.deleteProduct(id)
    }

    fun deleteAllProduct(): LiveData<MutableList<Product?>> {
        if(!::products.isInitialized) {
            products = MutableLiveData()
        }
        if(!::service.isInitialized) {
            service = ProductService()
        }
        products = service.deleteAllProduct()
        return products
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