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

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var userLoginInput:        EditText
    private lateinit var userPasswordInput:     EditText
    private lateinit var connectedStatus:       TextView
    private lateinit var spinner:               Spinner

    var ipServerSelected:                       String = "192.168.1.15"
    var ipServer =                              arrayOf("192.168.1.15", "192.168.43.209")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userLoginInput          = findViewById(R.id.EditTextUserLogin)
        userPasswordInput       = findViewById(R.id.EditTextUserPassword)
        connectedStatus         = findViewById(R.id.connectionState)
        spinner                 = findViewById(R.id.ips_spinner)

        val concatResult        = "None Refused None"
        connectedStatus.text    = concatResult

        val log = "bufalo@fire.com"
        val pass = "admin"
        userLoginInput.setText(log)
        userPasswordInput.setText(pass)

        //Spinner test
        spinner!!.onItemSelectedListener = this

        ArrayAdapter.createFromResource(
            this,
            R.array.ips_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        ipServerSelected = ipServer[position]
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {
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

        val model = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        model.getUser(login, password, ipServerSelected).observe(this,
            Observer<User> { user ->
                if (user != null) {
                    println(user.id)

                    val intent = Intent(applicationContext, RegisterActivity::class.java)
                    intent.putExtra("id", user.id)
                    intent.putExtra("login", user.login)
                    intent.putExtra("password", user.password)
                    intent.putExtra("connection", user.username)

                    intent.putExtra("server", ipServerSelected)
                    startActivity(intent)
                } else {
                    println("something wrong")
                    Toast.makeText(this, "Wrong login or password", Toast.LENGTH_LONG).show()
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

        intent.putExtra("server", ipServerSelected)
        startActivity(intent)
    }

    fun accessGenerator(view: View) {
        val intent = Intent(applicationContext, GenerateActivity::class.java)
        startActivity(intent)
    }
}
