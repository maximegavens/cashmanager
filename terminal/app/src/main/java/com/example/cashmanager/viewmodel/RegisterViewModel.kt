package com.example.cashmanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cashmanager.model.Product
import com.example.cashmanager.service.ProductService

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var service: ProductService
    private lateinit var products: MutableLiveData<MutableList<Product?>>

    fun getAllProduct(ipServer: String): LiveData<MutableList<Product?>> {
        if(!::products.isInitialized) {
            products = MutableLiveData()
            products.value = mutableListOf<Product?>()
        }
        if(!::service.isInitialized) {
            service = ProductService(ipServer)
        }
        getAll()
        return products
    }

    private fun getAll() {
        products = service.getAllProduct()
    }

    fun createProduct(p: Product, ipServer: String): LiveData<MutableList<Product?>> {
        if(!::products.isInitialized) {
            products = MutableLiveData()
            products.value = mutableListOf<Product?>()
        }
        if(!::service.isInitialized) {
            service = ProductService(ipServer)
        }
        create(p)
        return products
    }

    private fun create(p: Product) {
        val product = service.createProduct(p).value
        println(product!!.name)
        products.value!!.add(product)
    }

    fun updateProduct(id: Long, p: Product, ipServer: String): LiveData<MutableList<Product?>> {
        if(!::products.isInitialized) {
            products = MutableLiveData()
        }
        if(!::service.isInitialized) {
            service = ProductService(ipServer)
        }
        update(id, p)
        return products
    }

    private fun update(id: Long, p: Product) {
        val product = service.updateProduct(id, p).value

        products.value?.let {
            for (prod in it) {
                if (prod!!.id_product == id) {
                    prod.name = product!!.name
                    prod.price = product!!.price
                }
            }
        }
    }

    fun deleteProduct(id: Long, ipServer: String): LiveData<MutableList<Product?>> {
        if(!::products.isInitialized) {
            products = MutableLiveData()
        }
        if(!::service.isInitialized) {
            service = ProductService(ipServer)
        }
        delete(id)
        return products
    }

    private fun delete(id: Long) {
        val response = service.deleteProduct(id).value
        val productsCopy = products.value!!.toMutableList()
        println(response)

        productsCopy?.let {
            for (prod in it) {
                if (prod!!.id_product == id) {
                    products.value!!.remove(prod)
                }
            }
        }
    }
}