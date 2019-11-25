package com.example.cashmanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.cashmanager.service.PaymentService

class PaymentViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var service: PaymentService
    private lateinit var response: MutableLiveData<String>

    fun sendPayment(account: String, amount: String): MutableLiveData<String> {
        if(!::response.isInitialized) {
            response = MutableLiveData()
            response.value = String()
        }
        if(!::service.isInitialized) {
            service = PaymentService()
        }
        send(account, amount)
        return response
    }

    private fun send(account: String, amount: String) {
        response = service.sendPayment(account, amount)
    }
}