package com.example.cashmanager.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.cashmanager.R
import com.example.cashmanager.model.Server
import com.example.cashmanager.model.User
import com.example.cashmanager.viewmodel.LoginViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var serverIdInput: EditText
    private lateinit var serverPasswordInput: EditText
    private lateinit var connectedStatus: TextView
    private var SERVER_ID: String = "id"
    private var SERVER_PASSWORD: String = "password"
    private var SERVER_STATUS: String = "status"
    private var SERVER_LAST_CONNECTION: String = "connection"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        serverIdInput = findViewById(R.id.EditTextServerId)
        serverPasswordInput = findViewById(R.id.EditTextServerPassword)
        connectedStatus = findViewById(R.id.ViewTextConnectedStatus)
        connectedStatus.setText(SERVER_STATUS)

        val model = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        observeViewModel(model)
    }

    private fun observeViewModel(viewModel: LoginViewModel) {
        // Update the list when the data changes
        viewModel.getUsers().observe(this,
            Observer<List<User>> { users ->
                if (users != null) {
                    for (user in users) {
                        println(user.login)
                        println(user.password)
                        println(user.username)
                    }
                    // userAdapter.setProjectList(users)
                } else {
                    println("nothing")
                }
            })
    }

    fun login(view: View) {
        val id = serverIdInput.text.toString()
        val password = serverPasswordInput.text.toString()

        if(id.isEmpty()) {
            serverIdInput.error = "Server ID required"
            serverIdInput.requestFocus()
            return
        }
        if(password.isEmpty()) {
            serverPasswordInput.error = "Server password required"
            serverPasswordInput.requestFocus()
            return
        }

        val model = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        model.getServer(id, password).observe(this,
            Observer<Server> { server ->
                if (server != null) {
                    println(server.id)
                    println(server.password)
                    println(server.status)
                    println(server.last_connection)

                    val intent = Intent(applicationContext, RegisterActivity::class.java)
                    intent.putExtra(SERVER_ID, server.id)
                    intent.putExtra(SERVER_PASSWORD, server.password)
                    intent.putExtra(SERVER_STATUS, "CONNECTED")
                    intent.putExtra(SERVER_LAST_CONNECTION, server.last_connection)
                    startActivity(intent)
                } else {
                    println("something wrong")
                    val connection = "CONNECTION FAILED"
                    connectedStatus.setText(connection)
                }
            }
        )
    }

    fun accessSecondView(view: View) {
        val intent = Intent(applicationContext, RegisterActivity::class.java)
        intent.putExtra(SERVER_ID, "0")
        intent.putExtra(SERVER_PASSWORD, "admin")
        intent.putExtra(SERVER_STATUS, "CONNECTED")
        intent.putExtra(SERVER_LAST_CONNECTION, "today")
        startActivity(intent)
    }

    fun accessNfc(view: View) {
        val intent = Intent(applicationContext, ReceiverActivity::class.java)
        startActivity(intent)
    }
}
