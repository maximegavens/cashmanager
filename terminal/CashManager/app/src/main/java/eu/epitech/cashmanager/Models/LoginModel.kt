package eu.epitech.cashmanager.Models

import eu.epitech.cashmanager.Enums.LoginStatus

class LoginModel {
    var ip : String
    var password : String
    var status: LoginStatus

    init {
        ip = ""
        password = ""
        status = LoginStatus.REFUSED
    }

    fun ConnectToServer() {
        // Connect to the server
        status = LoginStatus.ESTABLISHED
    }
}