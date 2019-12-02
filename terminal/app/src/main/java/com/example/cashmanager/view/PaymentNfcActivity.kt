package com.example.cashmanager.view

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import com.example.cashmanager.R
import com.example.cashmanager.`object`.NfcManager
import com.example.cashmanager.viewmodel.PaymentViewModel
import java.util.*
import kotlin.concurrent.schedule
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_nfc_payment.*

class PaymentNfcActivity : BaseActivity() {

    private lateinit var connectionStatus:  TextView
    private lateinit var totalView:         TextView
    private lateinit var paymentStatusView: TextView
    private lateinit var paymentStatus:     TextView
    private lateinit var nfcDisabled:       TextView
    private lateinit var imageNfc:          ImageView
    private lateinit var imageSuccess:      ImageView
    private lateinit var imageFailed:       ImageView
    private lateinit var model:             PaymentViewModel
    private lateinit var resultMessage:     TextView
    private var incomingMessage:            String = ""
    private var nfcAdapter:                 NfcAdapter? = null
    private var pendingIntent:              PendingIntent? = null
    private var attempt =                   3
    private var paymentAttempt =            2

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_nfc_payment)
        super.onCreate(savedInstanceState)
        connectionStatus            = findViewById(R.id.connectionStatus)
        totalView                   = findViewById(R.id.billTotal)
        paymentStatusView           = findViewById(R.id.paymentStatus)
        imageSuccess                = findViewById(R.id.imageSuccess)
        imageFailed                 = findViewById(R.id.imageFailed)
        paymentStatus               = findViewById(R.id.paymentStatus)
        nfcDisabled                 = findViewById(R.id.nfcDisabled)
        resultMessage               = findViewById(R.id.scanTextView)
        imageNfc                    = findViewById(R.id.imageNfc)
        connectionStatus.text       = "connection IN PROGRESS"
        paymentStatus.text          = "Waiting for payment..."
        imageSuccess.visibility     = View.INVISIBLE
        imageFailed.visibility      = View.INVISIBLE
        nfcAdapter                  = NfcAdapter.getDefaultAdapter(this)
        pendingIntent               = PendingIntent.getActivity(this, 0, Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)
        if(nfcAdapter != null && nfcAdapter!!.isEnabled) {
            Toast.makeText(this, "Nfc Reader is enable", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Nfc Reader is disable", Toast.LENGTH_SHORT).show()
            imageNfc.visibility     = View.INVISIBLE
            resultMessage.visibility = View.INVISIBLE
            imageFailed.visibility  = View.VISIBLE
            nfcDisabled.visibility  = View.VISIBLE
            Timer("bill charge", false).schedule(3000) {
                backToRegister()
            }
        }
        model = ViewModelProviders.of(this).get(PaymentViewModel::class.java)
        updateTotal()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (resultMessage != null) {
            progressBar.visibility = View.VISIBLE
            setIntent(intent)
            incomingMessage = NfcManager.resolveIntentAndGetTagView(intent)
            println(incomingMessage)
            imageSuccess.visibility = View.VISIBLE
            imageNfc.visibility = View.INVISIBLE
            model.sendPayment(incomingMessage, totalView.text.toString()).observe(this,
                Observer<String> { message ->
                    println(message)
                    progressBar.visibility = View.INVISIBLE
                    if (message == "payment succeed") {
                        imageSuccess.visibility = View.VISIBLE
                        paymentStatus.text = "Payment success"
                        totalView.text = "0"

                    } else if (paymentAttempt != 0)  {
                        if (message == "payment refused") {
                            resultMessage!!.text = "payment refused"
                        } else {
                            resultMessage!!.text = "card reference not found"
                        }
                        imageFailed.visibility = View.VISIBLE
                        paymentStatus.text = "payment error"
                        paymentAttempt = paymentAttempt.minus(1)
                    } else {
                        imageFailed.visibility = View.VISIBLE
                        paymentStatus.text = "payment error"
                        resultMessage!!.text = "back to authentication"
                        backToAuthentication()
                    }
                })
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
                    attempt = attempt.minus(1)
                    if (attempt == 0) {
                        Toast.makeText(this, "Sorry connection server refused, back to authentication proceed...", Toast.LENGTH_SHORT).show()
                        backToAuthentication()
                    } else {
                        Toast.makeText(this, "Start new connection, left ".plus(attempt).plus(" attempt."), Toast.LENGTH_SHORT).show()
                        updateTotal()
                    }
                }
            })
    }

    private fun backToRegister() {
        val intent = Intent(applicationContext, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun backToAuthentication() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}