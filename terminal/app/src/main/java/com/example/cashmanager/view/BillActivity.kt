package com.example.cashmanager.view

import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cashmanager.R
import com.example.cashmanager.model.RegisterArticle
import com.example.cashmanager.service.CartBillAdapter

class BillActivity : AppCompatActivity() {

    private lateinit var connectedStatus:   TextView
    private lateinit var cartView:          ListView
    private lateinit var totalView:         TextView
    private lateinit var adapter:           CartBillAdapter

    private lateinit var cart:              Array<RegisterArticle>
    private lateinit var total:             String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill)

        val intent              = getIntent()
        val id                  = intent.getStringExtra("id") ?: "None"
        val status              = intent.getStringExtra("status") ?: "None"
        val lastConnection      = intent.getStringExtra("last connection") ?: "None"
        val concatResult        = id.plus(" ").plus(" ").plus(status).plus(" ").plus(lastConnection)

        total   = intent.getStringExtra("total") ?: "None"
        cart    = intent.getSerializableExtra("cart") as Array<RegisterArticle>
        adapter = CartBillAdapter(this, cart.toList())

        connectedStatus     = findViewById(R.id.connectionState)
        cartView            = findViewById(R.id.billList)
        totalView           = findViewById(R.id.billTotal)

        connectedStatus.text    = concatResult
        cartView.adapter = adapter
        totalView.text = total
    }
}