package eu.epitech.cashmanager.Activities

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import eu.epitech.cashmanager.Enums.LoginStatus
import eu.epitech.cashmanager.R
import eu.epitech.cashmanager.ViewModels.LoginViewModel

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        viewModel.Status.observe(this, Observer {
            when(it) {
                LoginStatus.ESTABLISHED -> {
                    findViewById<TextView>(R.id.ConnectionStatusText).text = "Connection established"
                    findViewById<ImageView>(R.id.ConnectionStatusIcon).setImageResource(R.drawable.login_status_connected)
                }
                LoginStatus.REFUSED -> {
                    findViewById<TextView>(R.id.ConnectionStatusText).text = "Connection refused"
                    findViewById<ImageView>(R.id.ConnectionStatusIcon).setImageResource(R.drawable.login_status_error)
                }
                LoginStatus.PROGRESS -> {
                    findViewById<TextView>(R.id.ConnectionStatusText).text = "Connection in progress"
                    findViewById<ImageView>(R.id.ConnectionStatusIcon).setImageResource(R.drawable.login_status_connecting)
                }
            }
        })
    }

    public fun try_connect(view: View) {
        val viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        val ip = findViewById<TextView>(R.id.IpAddress).text.toString()
        val password = findViewById<TextView>(R.id.Password).text.toString()
        viewModel.ConnectToServer(ip, password)
    }
}
