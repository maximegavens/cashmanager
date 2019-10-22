package eu.epitech.cashmanager.ViewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.epitech.cashmanager.Enums.LoginStatus
import eu.epitech.cashmanager.Networking.ApiService
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    val Api = ApiService.create()
    val Status = MutableLiveData<LoginStatus>()

    init {
        Status.value = LoginStatus.REFUSED
    }

    fun ConnectToServer(ip: String, password: String) {
        Status.value = LoginStatus.PROGRESS
        viewModelScope.launch {
            val users = Api.getUsers()
            Log.d("TAG", users.toString())
            Status.value = LoginStatus.ESTABLISHED
        }
    }
}