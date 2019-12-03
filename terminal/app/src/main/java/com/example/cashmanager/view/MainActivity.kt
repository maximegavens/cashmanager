package com.example.cashmanager.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.cashmanager.R
import com.example.cashmanager.model.User
import com.example.cashmanager.viewmodel.LoginViewModel

class MainActivity : BaseActivity(){

    private lateinit var userLoginInput:        EditText
    private lateinit var userPasswordInput:     EditText
    private lateinit var ipServerInput:         EditText
    private lateinit var connectedStatus:       TextView
    private lateinit var model:                 LoginViewModel
    private lateinit var errorMessage:          TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        super.onCreate(savedInstanceState)
        userLoginInput          = findViewById(R.id.EditTextUserLogin)
        userPasswordInput       = findViewById(R.id.EditTextUserPassword)
        ipServerInput           = findViewById(R.id.EditTextIPServer)
        connectedStatus         = findViewById(R.id.connectionStatus)
        errorMessage            = findViewById(R.id.errorMessage)
        connectedStatus.text    = "connection REFUSED"
        errorMessage.visibility = View.INVISIBLE

        val log = "bufalo@fire.com"     //TODO to remove
        val pass = "admin"              //TODO to remove
        val ip1 = "192.168.43.209"      //TODO to remove
        val ip2 = "192.168.1.15"        //TODO to remove
        userLoginInput.setText(log)     //TODO to remove
        userPasswordInput.setText(pass) //TODO to remove
        ipServerInput.setText(ip1)

        model = ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    fun login(view: View) {
        val login = userLoginInput.text.toString()
        val password = userPasswordInput.text.toString()
        val ip = ipServerInput.text.toString()

        if (login.isEmpty()) {
            userLoginInput.error = "user login required"
            userLoginInput.requestFocus()
            return
        }
        if (password.isEmpty()) {
            userPasswordInput.error = "user password required"
            userPasswordInput.requestFocus()
            return
        }
        if (ip.isEmpty()) {
            ipServerInput.error = "ip server is required"
            ipServerInput.requestFocus()
        }

        progressBar.visibility = View.VISIBLE

        model.getUser(login, password, ip).observe(this,
            Observer<User> { user ->
                progressBar.visibility = View.INVISIBLE
                if (user != null) {
                    connectedStatus.text = "connection REFUSED"
                    val intent = Intent(applicationContext, RegisterActivity::class.java)
                    startActivity(intent)
                } else {
                    connectedStatus.text = "connection REFUSED"
                    errorMessage.visibility = View.VISIBLE
                }
            }
        )
    }
}
