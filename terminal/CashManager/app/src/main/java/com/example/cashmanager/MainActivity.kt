package com.example.cashmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.cashmanager.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
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
        RetrofitClient.instance.login(id, password).enqueue(object: Callback<Any>{
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
        })
    }

    fun getUsers() {
        RetrofitClient.instance.getUsers().enqueue(object: Callback<List<User>>{
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                println("success")
                val userList = response.body()
                userList?.let {
                    for( user in it) {
                        println(user)
                    }
                }
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                println(t.message)
            }
        })
    }
}
