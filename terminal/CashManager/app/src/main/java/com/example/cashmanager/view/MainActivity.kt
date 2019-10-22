package com.example.cashmanager.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.cashmanager.R
import com.example.cashmanager.model.Server
import com.example.cashmanager.model.User
import com.example.cashmanager.viewmodel.LoginViewModel

class MainActivity : AppCompatActivity() {
    //private lateinit var userAdapter: UserAdapter
    private lateinit var serverIdInput: EditText
    private lateinit var serverPasswordInput: EditText
    private lateinit var connectedStatus: EditText
    private var CONNECTED_STATUS: String = "status"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        serverIdInput = findViewById(R.id.EditTextServerId)
        serverPasswordInput = findViewById(R.id.EditTextServerPassword)
        connectedStatus = findViewById(R.id.EditTextConnectedStatus)
        connectedStatus.setText(CONNECTED_STATUS)

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

                    val intent = Intent(applicationContext, CashRegisterActivity::class.java)
                    intent.putExtra(CONNECTED_STATUS, "connected")
                    startActivity(intent)
                } else {
                    println("la")
                    val intent = Intent(applicationContext, CashRegisterActivity::class.java)
                    intent.putExtra(CONNECTED_STATUS, "refused")
                    startActivity(intent)
                }
            })
/*        RetrofitClient.instance.login(id, password).enqueue(object: Callback<Any>{
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    println("post success")

                    val intent = Intent(applicationContext, CashRegisterActivity::class.java)
                    intent.putExtra(CONNECTED_STATUS, "connected")
                    startActivity(intent)
                }
                override fun onFailure(call: Call<Any>, t: Throwable) {
                    println("post fail")
                    println(t.message)
                    val intent = Intent(applicationContext, CashRegisterActivity::class.java)
                    intent.putExtra(CONNECTED_STATUS, "refused")
                    startActivity(intent)
                }
        })*/
    }
}
