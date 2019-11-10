package com.example.cashmanager.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.cashmanager.R
import com.example.cashmanager.model.RegisterArticle
import androidx.constraintlayout.widget.*
import com.example.cashmanager.service.CartRegisterAdapter

class RegisterActivity : AppCompatActivity() {

    private var             cart:           MutableList<RegisterArticle> = mutableListOf<RegisterArticle>()
    private var             total:          Double = 0.0

    private lateinit var articleNameInput:  EditText
    private lateinit var articlePriceInput: EditText
    private lateinit var connectedStatus:   TextView
    private lateinit var cartView:          ListView
    private lateinit var adapter:           CartRegisterAdapter
    private lateinit var totalView:         TextView
    private lateinit var totalAndPayLayout: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val intent              = getIntent()
        val id                  = intent.getStringExtra("id") ?: "None"
        val status              = intent.getStringExtra("status") ?: "None"
        val lastConnection      = intent.getStringExtra("last connection") ?: "None"
        val concatResult        = id.plus(" ").plus(" ").plus(status).plus(" ").plus(lastConnection)

        connectedStatus     = findViewById(R.id.connectionState)
        articleNameInput    = findViewById(R.id.EditTextArticleName)
        articlePriceInput   = findViewById(R.id.EditNumberArticlePrice)
        adapter             = CartRegisterAdapter(this, cart)
        cartView            = findViewById(R.id.ArticleListView)
        totalView           = findViewById(R.id.TotalPrice)
        totalAndPayLayout   = findViewById(R.id.totalAndPay)

        connectedStatus.text    = concatResult
        cartView.adapter        = adapter
    }

    fun addArticle(view: View) {
        val name        = articleNameInput.text.toString()
        val price       = articlePriceInput.text.toString()
        val newArticle  = RegisterArticle(name, price)
        val priceNumber = price.toDoubleOrNull() ?: return

        total = total.plus(priceNumber)
        totalView.text = total.toString()
        cart.add(newArticle)
        adapter.notifyDataSetChanged()

        totalAndPayLayout.visibility = View.VISIBLE
    }

    fun removeArticle(view: View) {
        val np = getNameAndPriceFromArticleView(view) ?: return
        val copyArticle = cart.toList()

        for (art in copyArticle) {
            if (art.name == np.first && art.price == np.second) {
                total = total.minus(art.price.toDouble())
                totalView.text = total.toString()
                cart.remove(art)
                adapter.notifyDataSetChanged()
                if (cart.isEmpty()) totalAndPayLayout.visibility = View.INVISIBLE
                break
            }
        }
    }

    fun paymentProceed(view: View) {
        val intent = Intent(applicationContext, BillActivity::class.java)
        intent.putExtra("id", "0")
        intent.putExtra("status", "CONNECTED")
        intent.putExtra("last_connection", "today")

        intent.putExtra("total", total.toString())
        intent.putExtra("cart", cart.toTypedArray())
        startActivity(intent)
    }

    private fun getNameAndPriceFromArticleView(view: View): Pair<String, String>? {
        val parent = view.parent

        if (parent is ConstraintLayout) {
            var nameView    = parent.getViewById(R.id.articleName) ?: return null
            var priceView   = parent.getViewById(R.id.articlePrice) ?: return null

            nameView    = nameView as TextView
            priceView   = priceView as TextView

            val name    = nameView.text as String
            val price   = priceView.text as String

            return Pair(name, price)
        }
        return null
    }
}