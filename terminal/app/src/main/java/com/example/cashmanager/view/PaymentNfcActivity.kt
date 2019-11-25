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
import java.util.*
import kotlin.concurrent.schedule

class PaymentNfcActivity : AppCompatActivity() {

    private lateinit var connectedStatus:   TextView
    private lateinit var totalView:         TextView
    private lateinit var paymentStatusView: TextView
    private lateinit var paymentStatus:     TextView
    private lateinit var nfcDisabled:       TextView
    private lateinit var imageNfc:          ImageView
    private lateinit var imageSuccess:      ImageView
    private lateinit var imageFailed:       ImageView

    private lateinit var model:             PaymentViewModel

    // NFC Reader
    private var incomingMessage: TextView? = null
    private var nfcAdapter: NfcAdapter? = null
    private var pendingIntent: PendingIntent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc_payment)

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
        nfcDisabled         = findViewById(R.id.nfcDisabled)

        // NFC Reader
        incomingMessage     = findViewById(R.id.tv_in_message)
        imageNfc            = findViewById(R.id.imageNfc)

        connectedStatus.text    = concatResult
        totalView.text          = intent.getStringExtra("total") ?: "None"
        paymentStatus.text      = "Waiting for payment..."

        imageSuccess.visibility   = View.INVISIBLE
        imageFailed.visibility    = View.INVISIBLE

        // Nfc Initialize
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        pendingIntent = PendingIntent.getActivity(
            this, 0, Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0
        )
        if(nfcAdapter != null && nfcAdapter!!.isEnabled) {
            Toast.makeText(this, "Nfc Reader is enable", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Nfc Reader is disable", Toast.LENGTH_SHORT).show()
            imageNfc.visibility = View.INVISIBLE
            imageFailed.visibility = View.VISIBLE
            nfcDisabled.visibility = View.VISIBLE
            Timer("bill charge", false).schedule(4000) {
                backToRegister()
            }
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

    private fun backToRegister() {
        val intent = Intent(applicationContext, RegisterActivity::class.java)
        intent.putExtra("id", "0")
        intent.putExtra("status", "CONNECTED")
        intent.putExtra("last_connection", "today")

        startActivity(intent)
    }
}