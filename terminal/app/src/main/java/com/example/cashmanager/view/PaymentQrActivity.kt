package com.example.cashmanager.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.cashmanager.R
import com.example.cashmanager.viewmodel.PaymentViewModel
import com.google.zxing.integration.android.IntentIntegrator
import androidx.lifecycle.Observer

class PaymentQrActivity : BaseActivity() {
    private lateinit var connectionStatus:  TextView
    private lateinit var totalView:         TextView
    private lateinit var paymentStatusView: TextView
    private lateinit var paymentStatus:     TextView
    private lateinit var scanTextView:      TextView
    private lateinit var imageSuccess:      ImageView
    private lateinit var imageFailed:       ImageView
    private lateinit var model:             PaymentViewModel
    private var btnScan:                    Button? = null
    private var QRCardRefNumber:            String? = null
    private var CardExpNumber:              String? = null
    private var attempt =                   2
    private var paymentAttempt =            3

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_qr_payment)
        super.onCreate(savedInstanceState)
        connectionStatus            = findViewById(R.id.connectionStatus)
        totalView                   = findViewById(R.id.billTotal)
        paymentStatusView           = findViewById(R.id.paymentStatus)
        imageSuccess                = findViewById(R.id.imageSuccess)
        imageFailed                 = findViewById(R.id.imageFailed)
        paymentStatus               = findViewById(R.id.paymentStatus)
        btnScan                     = findViewById(R.id.cameraButton)
        scanTextView                = findViewById(R.id.messageReceive)
        connectionStatus.text       = "connection IN PROGRESS"
        paymentStatus.text          = "Waiting for payment..."
        imageSuccess.visibility     = View.INVISIBLE
        imageFailed.visibility      = View.INVISIBLE
        scanTextView.text           = ""
        btnScan!!.setOnClickListener {
            IntentIntegrator(this).initiateScan()
        }
        model = ViewModelProviders.of(this).get(PaymentViewModel::class.java)
        updateTotal()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "result content is empty", Toast.LENGTH_SHORT).show()
            } else {
                println(result.contents.toString())
                if (parseMessageReceive(result.contents.toString())) {
                    scanTextView.text           = "QRCard reference correct"
                    paymentStatus.text          = "Payment proceed..."
                    btnScan!!.visibility        = View.INVISIBLE
                    progressBar.visibility = View.VISIBLE
                    model.sendPayment(QRCardRefNumber!!, totalView.text.toString()).observe(this,
                        Observer<String> { message ->
                            progressBar.visibility = View.INVISIBLE
                            println("OBSERVER")
                            println(message)
                            if (message == "payment succeed") {
                                imageFailed.visibility = View.INVISIBLE
                                imageSuccess.visibility = View.VISIBLE
                                paymentStatus.text = "Payment success"
                                totalView.text = "0"

                            } else if (paymentAttempt != 0)  {
                                if (message == "payment refused") {
                                    scanTextView.text = "payment refused, left ".plus(paymentAttempt).plus(" attempt")
                                } else {
                                    scanTextView.text = "card reference not found, left ".plus(paymentAttempt).plus(" attempt")
                                }
                                imageFailed.visibility = View.VISIBLE
                                btnScan!!.visibility = View.VISIBLE
                                paymentStatus.text = "payment error"
                                paymentAttempt = paymentAttempt.minus(1)
                            } else {
                                imageFailed.visibility = View.VISIBLE
                                btnScan!!.visibility = View.VISIBLE
                                paymentStatus.text = "payment error"
                                scanTextView.text = "back to authentication"
                                backToAuthentication()
                            }
                        })
                } else {
                    scanTextView.text = "QRCard reference improper !"
                    imageFailed.visibility = View.VISIBLE
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

    private fun updateTotal() {
        progressBar.visibility = View.VISIBLE
        model.getTotal().observe(this,
            Observer<String> { total ->
                progressBar.visibility = View.INVISIBLE
                if (total != null) {
                    connectionStatus.text   = "connection ESTABLISHED"
                    totalView.text = total
                } else {
                    connectionStatus.text   = "connection REFUSED"
                    if (attempt == 0) {
                        Toast.makeText(this, "Sorry connection server refused, back to authentication proceed...", Toast.LENGTH_SHORT).show()
                        backToAuthentication()
                    } else {
                        Toast.makeText(this, "Start new connection, left ".plus(attempt).plus(" attempt."), Toast.LENGTH_SHORT).show()
                        updateTotal()
                    }
                    attempt = attempt.minus(1)
                }
            })
    }

    private fun backToAuthentication() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}