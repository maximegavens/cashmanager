package com.example.cashmanager.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.cashmanager.R
import com.example.cashmanager.model.Product
import com.example.cashmanager.viewmodel.PaymentViewModel
import com.google.zxing.integration.android.IntentIntegrator
import java.util.*
import kotlin.concurrent.schedule
import androidx.lifecycle.Observer

class PaymentQrActivity : AppCompatActivity() {

    private lateinit var connectedStatus:   TextView
    private lateinit var totalView:         TextView
    private lateinit var paymentStatusView: TextView
    private lateinit var paymentStatus:     TextView
    private lateinit var scanTextView:      TextView
    private lateinit var imageSuccess:      ImageView
    private lateinit var imageFailed:       ImageView

    private lateinit var model:             PaymentViewModel

    // QR code
    private var btnScan:            Button? = null
    private var QRCardRefNumber:    String? = null
    private var CardExpNumber:      String? = null
    private var total:              String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_payment)

        val intent              = getIntent()
        val id                  = intent.getStringExtra("id") ?: "None"
        val status              = intent.getStringExtra("status") ?: "None"
        val lastConnection      = intent.getStringExtra("last connection") ?: "None"
        val concatResult        = id.plus(" ").plus(" ").plus(status).plus(" ").plus(lastConnection)

        connectedStatus     = findViewById(R.id.connectionState)
        totalView           = findViewById(R.id.billTotal)
        paymentStatusView   = findViewById(R.id.paymentStatus)
        imageSuccess        = findViewById(R.id.imageSuccess)
        imageFailed         = findViewById(R.id.imageFailed)
        paymentStatus       = findViewById(R.id.paymentStatus)

        //QR code
        btnScan             = findViewById(R.id.cameraButton)
        scanTextView        = findViewById(R.id.messageReceive)

        connectedStatus.text    = concatResult
        totalView.text          = intent.getStringExtra("total") ?: "None"
        total                   = intent.getStringExtra("total") ?: "0.0"
        paymentStatus.text      = "Waiting for payment..."

        imageSuccess.visibility   = View.INVISIBLE
        imageFailed.visibility    = View.INVISIBLE
        scanTextView.visibility   = View.INVISIBLE

        btnScan!!.setOnClickListener {
            IntentIntegrator(this).initiateScan()

        }

        model = ViewModelProviders.of(this).get(PaymentViewModel::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "result content is empty", Toast.LENGTH_SHORT).show()
            } else {
                println(result.contents.toString())
                if (parseMessageReceive(result.contents.toString())) {
                    scanTextView.text = "QRCard reference correct !"
                    paymentStatus.text = "Payment proceed..."
                    scanTextView.visibility = View.VISIBLE
                    btnScan!!.visibility = View.INVISIBLE
                    model.sendPayment(QRCardRefNumber!!, total!!).observe(this,
                        Observer<String> { message ->
                            println("OBSERVER")
                            println(message)
                            if (message == "payment succeed") {
                                imageSuccess.visibility = View.VISIBLE
                                paymentStatus.text = "Payment success!"
                                totalView.text = "0"
                            } else {
                                imageFailed.visibility = View.VISIBLE
                                paymentStatus.text = "Payment error!"
                                scanTextView.text = "Payment refused"
                            }
                        })
                } else {
                    scanTextView.text = "QRCard reference improper !"
                    imageFailed.visibility = View.VISIBLE
                    scanTextView.visibility = View.VISIBLE

                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun parseMessageReceive(msg: String): Boolean {
        var QRCardRefNumberNoFilter: List<String>? = null
        var CardExpNumberNoFilter: List<String>? = null
        val result = msg.split("{", "}", ",")
        for (r in result) {
            val m = r.split('"', ':', ' ')
            println(m)
            if (m.contains("QRCardRefNumber")) QRCardRefNumberNoFilter = m
            if (m.contains("CardExpDate")) CardExpNumberNoFilter = m
        }
        if (QRCardRefNumberNoFilter.isNullOrEmpty()) {
            return false
        } else if (CardExpNumberNoFilter.isNullOrEmpty()) {
            return false
        } else {
            QRCardRefNumber = QRCardRefNumberNoFilter.filter { s -> s != "" }[1]
            CardExpNumber = CardExpNumberNoFilter.filter { s -> s != ""}[1]

            return true
        }
    }
}