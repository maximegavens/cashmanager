package com.example.cashmanager.view

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.cashmanager.R
import com.example.cashmanager.`object`.NfcManager
import com.example.cashmanager.viewmodel.PaymentViewModel
import com.example.cashmanager.viewmodel.RegisterViewModel
import com.google.zxing.integration.android.IntentIntegrator

class PaymentActivity : AppCompatActivity() {

    private lateinit var connectedStatus:   TextView
    private lateinit var totalView:         TextView
    private lateinit var paymentStatusView: TextView
    private lateinit var paymentModeView:   TextView
    private lateinit var paymentStatus:     TextView
    private lateinit var imageNfc:          ImageView
    private lateinit var imageSuccess:      ImageView
    private lateinit var imageFailed:       ImageView

    private lateinit var model:             PaymentViewModel

    private lateinit var server:            String

    //TODO split in two activity, one with qr other with scan

    // NFC Reader
    private var incomingMessage: TextView? = null
    private var nfcAdapter: NfcAdapter? = null
    private var pendingIntent: PendingIntent? = null

    // QR code
    private var btnScan: Button? = null
    private var scanTextView: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val intent              = getIntent()
        val id                  = intent.getStringExtra("id") ?: "None"
        val status              = intent.getStringExtra("status") ?: "None"
        val lastConnection      = intent.getStringExtra("last connection") ?: "None"
        val concatResult        = id.plus(" ").plus(" ").plus(status).plus(" ").plus(lastConnection)

        server = intent.getStringExtra("server") ?: "localhost"

        connectedStatus     = findViewById(R.id.connectionState)
        totalView           = findViewById(R.id.billTotal)
        paymentStatusView   = findViewById(R.id.paymentStatus)
        paymentModeView     = findViewById(R.id.paymentMode)
        imageSuccess        = findViewById(R.id.imageSuccess)
        imageFailed         = findViewById(R.id.imageFailed)
        paymentStatus       = findViewById(R.id.paymentStatus)
        // NFC Reader
        incomingMessage     = findViewById(R.id.tv_in_message)
        imageNfc            = findViewById(R.id.imageNfc)
        //QR code
        btnScan             = findViewById(R.id.cameraButton)
        scanTextView        = findViewById(R.id.cameraPreview)

        connectedStatus.text    = concatResult
        totalView.text          = intent.getStringExtra("total") ?: "None"
        paymentModeView.text    = intent.getStringExtra("mode") ?: "None"
        paymentStatus.text      = "Waiting for payment..."

        imageSuccess!!.visibility   = View.INVISIBLE
        imageFailed!!.visibility    = View.INVISIBLE

        if (paymentModeView.text == "nfc") {
            nfcAdapter = NfcAdapter.getDefaultAdapter(this)
            pendingIntent = PendingIntent.getActivity(
                this, 0, Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0
            )
            if(nfcAdapter != null && nfcAdapter!!.isEnabled) {
                Toast.makeText(this, "Nfc Reader is enable", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Nfc Reader is disable", Toast.LENGTH_SHORT).show()
            }
            btnScan!!.visibility = View.INVISIBLE
            scanTextView!!.visibility = View.INVISIBLE
        } else if (paymentModeView.text == "QR code") {
            incomingMessage!!.visibility = View.INVISIBLE
            imageNfc!!.visibility = View.INVISIBLE
            initfunction()
        } else {
            btnScan!!.visibility = View.INVISIBLE
            scanTextView!!.visibility = View.INVISIBLE
            incomingMessage!!.visibility = View.INVISIBLE
            imageNfc!!.visibility = View.INVISIBLE
        }

        model = ViewModelProviders.of(this).get(PaymentViewModel::class.java)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        if (incomingMessage != null) {
            setIntent(intent)
            incomingMessage!!.text = NfcManager.resolveIntentAndGetTagView(intent)
            imageSuccess.visibility = View.VISIBLE
            imageNfc.visibility = View.INVISIBLE
        }
    }

    override fun onResume() {
        if (nfcAdapter != null) {
            nfcAdapter!!.enableForegroundDispatch(this, pendingIntent, null, null)
        }

        super.onResume()
    }

    override fun onPause() {
        if (nfcAdapter != null) {
            nfcAdapter!!.disableForegroundDispatch(this)
        }

        super.onPause()
    }

    private fun initfunction() {
        if (btnScan != null) {
            btnScan!!.setOnClickListener {
                initScan()
            }
        }
    }

    private fun initScan() {
        IntentIntegrator(this).initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null && scanTextView != null) {
            if (result.contents == null) {
                Toast.makeText(this, "result content is empty", Toast.LENGTH_SHORT).show()
            } else {
                scanTextView!!.setText(result.contents.toString())
                imageSuccess.visibility = View.VISIBLE
                model.sendPayment() //TODO extract account code and amount of bill, make a various display in function of server result

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}