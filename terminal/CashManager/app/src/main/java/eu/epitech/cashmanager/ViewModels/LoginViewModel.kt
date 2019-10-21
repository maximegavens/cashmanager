package eu.epitech.cashmanager.ViewModels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eu.epitech.cashmanager.Enums.LoginStatus

class LoginViewModel : ViewModel() {
    var Status = MutableLiveData<LoginStatus>()

    init {
        Status.value = LoginStatus.REFUSED
    }

    fun ConnectToServer(ip: String, password: String) {
        Status.value = LoginStatus.ESTABLISHED
        println("CONNECTION -> ${ip}; ${password}")
    }
}