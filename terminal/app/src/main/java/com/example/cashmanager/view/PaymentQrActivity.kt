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

class PaymentQrActivity : AppCompatActivity() {

    private lateinit var connectedStatus:   TextView
    private lateinit var totalView:         TextView
    private lateinit var paymentStatusView: TextView
    private lateinit var paymentStatus:     TextView
    private lateinit var imageSuccess:      ImageView
    private lateinit var imageFailed:       ImageView

    private lateinit var model:             PaymentViewModel

    // QR code
    private var btnScan: Button? = null
    private var scanTextView: EditText? = null

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
        scanTextView        = findViewById(R.id.cameraPreview)

        connectedStatus.text    = concatResult
        totalView.text          = intent.getStringExtra("total") ?: "None"
        paymentStatus.text      = "Waiting for payment..."

        imageSuccess.visibility   = View.INVISIBLE
        imageFailed.visibility    = View.INVISIBLE

        initScan()

        model = ViewModelProviders.of(this).get(PaymentViewModel::class.java)
    }

    private fun initScan() {
        if (btnScan != null) {
            btnScan!!.setOnClickListener {
                IntentIntegrator(this).initiateScan()

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null && scanTextView != null) {
            if (result.contents == null) {
                Toast.makeText(this, "result content is empty", Toast.LENGTH_SHORT).show()
            } else {
                scanTextView!!.setText(result.contents.toString())
                imageSuccess.visibility = View.VISIBLE
               /* model.sendPayment()*/ //TODO extract account code and amount of bill, make a various display in function of server result

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}