package com.example.cashmanager.view

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cashmanager.R
import com.example.cashmanager.service.NfcManager.resolveIntentAndGetTagView
import android.widget.Button
import android.widget.EditText
import com.google.zxing.integration.android.IntentIntegrator


class ReceiverActivity : AppCompatActivity() {

    // NFC Reader
    private lateinit var IncomingMessage: TextView
    private var nfcAdapter: NfcAdapter? = null
    private lateinit var pendingIntent: PendingIntent

    // QR code
    private lateinit var btnScan: Button
    private lateinit var scanTextView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.cashmanager.R.layout.activity_receiver)

        // NFC Reader
        IncomingMessage = findViewById(com.example.cashmanager.R.id.tv_in_message)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        pendingIntent = PendingIntent.getActivity(
            this, 0, Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0
        )

        if(nfcAdapter != null && nfcAdapter!!.isEnabled) {
            Toast.makeText(this, "Nfc Reader is enable", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Nfc Reader is disable", Toast.LENGTH_SHORT).show()
        }

        //QR code
        btnScan = findViewById(R.id.cameraButton)
        scanTextView = findViewById(R.id.cameraPreview)
        initfunction()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        setIntent(intent)
        IncomingMessage.text = resolveIntentAndGetTagView(intent)
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
        btnScan.setOnClickListener {
            initScan()
        }
    }

    private fun initScan() {
        IntentIntegrator(this).initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "result content is empty", Toast.LENGTH_SHORT).show()
            } else {
                scanTextView.setText(result.contents.toString())
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}