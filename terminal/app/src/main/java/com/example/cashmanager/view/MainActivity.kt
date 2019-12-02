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
    private lateinit var connectedStatus:       TextView
    private lateinit var model:                 LoginViewModel
    private lateinit var errorMessage:          TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        super.onCreate(savedInstanceState)
        userLoginInput          = findViewById(R.id.EditTextUserLogin)
        userPasswordInput       = findViewById(R.id.EditTextUserPassword)
        connectedStatus         = findViewById(R.id.connectionStatus)
        errorMessage            = findViewById(R.id.errorMessage)
        connectedStatus.text    = "connection REFUSED"
        errorMessage.visibility = View.INVISIBLE

        val log = "bufalo@fire.com"     //TODO ot remove
        val pass = "admin"              //TODO ot remove
        userLoginInput.setText(log)     //TODO ot remove
        userPasswordInput.setText(pass) //TODO ot remove

        model = ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    fun login(view: View) {
        val login = userLoginInput.text.toString()
        val password = userPasswordInput.text.toString()

        if(login.isEmpty()) {
            userLoginInput.error = "user ID required"
            userLoginInput.requestFocus()
            return
        }
        if(password.isEmpty()) {
            userPasswordInput.error = "user password required"
            userPasswordInput.requestFocus()
            return
        }

        progressBar.visibility = View.VISIBLE

        model.getUser(login, password).observe(this,
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

    /*fun accessSecondView(view: View) {
        val intent = Intent(applicationContext, RegisterActivity::class.java)
        intent.putExtra("id", "0")
        intent.putExtra("password", "admin")
        intent.putExtra("status", "CONNECTED")
        intent.putExtra("connection", "today")

        startActivity(intent)
    }

    fun accessGenerator(view: View) {
        val intent = Intent(applicationContext, GenerateActivity::class.java)
        startActivity(intent)
    }*/
}
