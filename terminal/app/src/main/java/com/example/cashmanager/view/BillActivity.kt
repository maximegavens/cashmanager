package com.example.cashmanager.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.cashmanager.R
import com.example.cashmanager.model.Product
import com.example.cashmanager.adapter.CartBillAdapter
import com.example.cashmanager.viewmodel.BillViewModel
import java.util.*
import kotlin.concurrent.schedule
import androidx.lifecycle.Observer

class BillActivity : AppCompatActivity() {

    private lateinit var connectedStatus:   TextView
    private lateinit var cartView:          ListView
    private lateinit var totalView:         TextView
    private lateinit var adapter:           CartBillAdapter

    private lateinit var mode:              String
    private lateinit var server:            String

    private lateinit var model:             BillViewModel

    private var cart =                      mutableListOf<Product>()
    private var total =                     0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill)

        val intent              = getIntent()
        val id                  = intent.getStringExtra("id") ?: "None"
        val status              = intent.getStringExtra("status") ?: "None"
        val lastConnection      = intent.getStringExtra("last connection") ?: "None"
        val concatResult        = id.plus(" ").plus(" ").plus(status).plus(" ").plus(lastConnection)

        /*cart    = intent.getSerializableExtra("cart") as Array<Product>*/
        mode    = intent.getStringExtra("mode") ?: "nfc"
        server  = intent.getStringExtra("server") ?: "localhost"

        connectedStatus     = findViewById(R.id.connectionState)
        cartView            = findViewById(R.id.billList)
        totalView           = findViewById(R.id.billTotal)

        adapter = CartBillAdapter(this, cart)
        connectedStatus.text    = concatResult
        cartView.adapter = adapter

        model = ViewModelProviders.of(this).get(BillViewModel::class.java)
        updateBillAndForward()
    }

    private fun updateBillAndForward() {
        model.getAllProduct().observe(this,
            Observer<MutableList<Product?>> { products ->
                total = 0.0
                if (products != null) {
                    for (product in products) {
                        println(product?.name)
                        cart.add(product!!)
                        total = total.plus(product.price.toDouble())
                    }
                    totalView.text = total.toString()
                    adapter.notifyDataSetChanged()
                    Timer("bill charge", false).schedule(4000) {
                        println("timer success")
                        paymentProceed()
                    }
                } else {
                    Toast.makeText(this, "Products is empty or error server", Toast.LENGTH_LONG).show()
                    backToRegister()
                }
            })
    }

    private fun paymentProceed() {
        val intent: Intent

        if (mode == "nfc") {
            intent = Intent(applicationContext, PaymentNfcActivity::class.java)
        } else {
            intent = Intent(applicationContext, PaymentQrActivity::class.java)
        }
        intent.putExtra("id", "0")
        intent.putExtra("status", "CONNECTED")
        intent.putExtra("last_connection", "today")

        intent.putExtra("total", total.toString())
        intent.putExtra("server", server)
        startActivity(intent)
    }

    private fun backToRegister() {
        val intent = Intent(applicationContext, RegisterActivity::class.java)
        intent.putExtra("id", "0")
        intent.putExtra("status", "CONNECTED")
        intent.putExtra("last_connection", "today")

        intent.putExtra("total", total.toString())
        intent.putExtra("mode", mode)
        startActivity(intent)
    }
}