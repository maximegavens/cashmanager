package com.example.cashmanager.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.cashmanager.R
import com.example.cashmanager.model.User
import com.example.cashmanager.tool.GenerateActivity
import com.example.cashmanager.viewmodel.LoginViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var userLoginInput:        EditText
    private lateinit var userPasswordInput:     EditText
    private lateinit var connectedStatus:       TextView
    private lateinit var spinner:               Spinner
    lateinit var progressBar:                   ProgressBar

    private lateinit var model:                 LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userLoginInput          = findViewById(R.id.EditTextUserLogin)
        userPasswordInput       = findViewById(R.id.EditTextUserPassword)
        connectedStatus         = findViewById(R.id.connectionState)
        progressBar             = findViewById(R.id.progress_bar)

        val concatResult        = "None Refused None"
        connectedStatus.text    = concatResult

        val log = "bufalo@fire.com"
        val pass = "admin"
        userLoginInput.setText(log)
        userPasswordInput.setText(pass)
        progressBar.visibility = View.INVISIBLE

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
                    println(user.id)

                    val intent = Intent(applicationContext, RegisterActivity::class.java)
                    intent.putExtra("id", user.id)
                    intent.putExtra("login", user.login)
                    intent.putExtra("password", user.password)
                    intent.putExtra("connection", user.username)

                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Server connection failed", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    fun accessSecondView(view: View) {
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
    }
}
