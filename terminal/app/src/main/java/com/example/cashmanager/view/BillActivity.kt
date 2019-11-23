package com.example.cashmanager.view

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cashmanager.R
import com.example.cashmanager.model.Product
import com.example.cashmanager.adapter.CartBillAdapter
import java.util.*
import kotlin.concurrent.schedule

class BillActivity : AppCompatActivity() {

    private lateinit var connectedStatus:   TextView
    private lateinit var cartView:          ListView
    private lateinit var totalView:         TextView
    private lateinit var adapter:           CartBillAdapter

    private lateinit var cart:              Array<Product>
    private lateinit var total:             String
    private lateinit var mode:              String
    private lateinit var server:            String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill)

        //TODO recover the current bill from server, then compute the amount of bill

        val intent              = getIntent()
        val id                  = intent.getStringExtra("id") ?: "None"
        val status              = intent.getStringExtra("status") ?: "None"
        val lastConnection      = intent.getStringExtra("last connection") ?: "None"
        val concatResult        = id.plus(" ").plus(" ").plus(status).plus(" ").plus(lastConnection)

        total   = intent.getStringExtra("total") ?: "None"
        cart    = intent.getSerializableExtra("cart") as Array<Product>
        mode    = intent.getStringExtra("mode") ?: "None"
        server  = intent.getStringExtra("server") ?: "localhost"

        connectedStatus     = findViewById(R.id.connectionState)
        cartView            = findViewById(R.id.billList)
        totalView           = findViewById(R.id.billTotal)

        adapter = CartBillAdapter(this, cart.toList())
        connectedStatus.text    = concatResult
        cartView.adapter = adapter
        totalView.text = total

        // wait
        Timer("bill charge", false).schedule(4000) {
            println("timer success")
            paymentProceed()
        }
    }

    private fun paymentProceed() {
        val intent = Intent(applicationContext, PaymentActivity::class.java)
        intent.putExtra("id", "0")
        intent.putExtra("status", "CONNECTED")
        intent.putExtra("last_connection", "today")

        intent.putExtra("total", total.toString())
        intent.putExtra("mode", mode)
        intent.putExtra("server", server)
        startActivity(intent)
    }
}