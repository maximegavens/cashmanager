package com.example.cashmanager.view

import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cashmanager.R
import com.example.cashmanager.model.RegisterArticle
import com.example.cashmanager.service.CartBillAdapter

class PaymentActivity : AppCompatActivity() {

    private lateinit var connectedStatus:   TextView
    private lateinit var totalView:         TextView
    private lateinit var paymentStatusView: TextView
    private lateinit var paymentView:       TextView

    private lateinit var total:             String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val intent              = getIntent()
        val id                  = intent.getStringExtra("id") ?: "None"
        val status              = intent.getStringExtra("status") ?: "None"
        val lastConnection      = intent.getStringExtra("last connection") ?: "None"
        val concatResult        = id.plus(" ").plus(" ").plus(status).plus(" ").plus(lastConnection)

        total   = intent.getStringExtra("total") ?: "None"

        connectedStatus     = findViewById(R.id.connectionState)
        totalView           = findViewById(R.id.billTotal)
        paymentStatusView   = findViewById(R.id.paymentStatus)
        paymentView         = findViewById(R.id.payment)

        connectedStatus.text    = concatResult
        totalView.text = total
    }
}