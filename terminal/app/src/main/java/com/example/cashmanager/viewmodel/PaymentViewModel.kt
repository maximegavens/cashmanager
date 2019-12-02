package com.example.cashmanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cashmanager.service.PaymentService
import com.example.cashmanager.service.ProductService

class PaymentViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var service: PaymentService
    private lateinit var response: MutableLiveData<String>
    private lateinit var total: MutableLiveData<String>

    fun sendPayment(account: String, amount: String): MutableLiveData<String> {
        if(!::response.isInitialized) {
            response = MutableLiveData()
            response.value = String()
        }
        send(account, amount)
        return response
    }

    private fun send(account: String, amount: String) {
        service = PaymentService()
        response = service.sendPayment(account, amount)
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