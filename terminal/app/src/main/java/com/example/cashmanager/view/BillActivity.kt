package com.example.cashmanager.view

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.cashmanager.R
import com.example.cashmanager.model.Product
import com.example.cashmanager.adapter.CartBillAdapter
import com.example.cashmanager.viewmodel.BillViewModel
import java.util.*
import kotlin.concurrent.schedule
import androidx.lifecycle.Observer

class BillActivity : BaseActivity() {

    private lateinit var model:             BillViewModel
    private lateinit var connectionStatus:  TextView
    private lateinit var cartView:          ListView
    private lateinit var totalView:         TextView
    private lateinit var adapter:           CartBillAdapter
    private var mode =                      "nfc"
    private var cart =                      mutableListOf<Product>()
    private var attempt =                   3

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_bill)
        super.onCreate(savedInstanceState)
        connectionStatus        = findViewById(R.id.connectionStatus)
        cartView                = findViewById(R.id.billList)
        totalView               = findViewById(R.id.billTotal)
        mode                    = getIntent().getStringExtra("mode") ?: "nfc"
        adapter                 = CartBillAdapter(this, cart)

        cartView.adapter        = adapter
        model                   = ViewModelProviders.of(this).get(BillViewModel::class.java)
        updateBillAndForward()
    }

    private fun updateTotal() {
        connectionStatus.text = "connection IN PROGRESS"
        model.getTotal().observe(this,
            Observer<String> { total ->
                if (total != null) {
                    connectionStatus.text   = "connection ESTABLISHED"
                    totalView.text = total
                } else {
                    connectionStatus.text   = "connection REFUSED"
                }
            })
    }

    private fun updateBillAndForward() {
        updateTotal()
        connectionStatus.text = "connection IN PROGRESS"
        model.getAllProduct().observe(this,
            Observer<MutableList<Product?>> { products ->
                if (products != null) {
                    connectionStatus.text   = "connection ESTABLISHED"
                    for (product in products) {
                        cart.add(product!!)
                    }
                    adapter.notifyDataSetChanged()
                    Timer("bill charge", false).schedule(4000) {
                        paymentProceed()
                    }
                } else {
                    connectionStatus.text   = "connection REFUSED"
                    attempt = attempt.minus(1)
                    if (attempt == 0) {
                        Toast.makeText(this, "Sorry connection server refused, back to authentication proceed...", Toast.LENGTH_SHORT).show()
                        backToAuthentication()
                    } else {
                        Toast.makeText(this, "Start new connection, left ".plus(attempt).plus(" attempt."), Toast.LENGTH_SHORT).show()
                        updateBillAndForward()
                    }
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
        startActivity(intent)
    }

    private fun backToAuthentication() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}