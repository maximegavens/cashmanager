package com.example.cashmanager.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.cashmanager.R
import androidx.constraintlayout.widget.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.cashmanager.model.Product
import com.example.cashmanager.adapter.CartRegisterAdapter
import com.example.cashmanager.viewmodel.RegisterViewModel

class RegisterActivity : BaseActivity() {
    private lateinit var articleNameInput:  EditText
    private lateinit var articlePriceInput: EditText
    private lateinit var connectionStatus:  TextView
    private lateinit var cartView:          ListView
    private lateinit var adapter:           CartRegisterAdapter
    private lateinit var totalView:         TextView
    private lateinit var totalAndPayLayout: ConstraintLayout
    private lateinit var paymentMode:       RadioGroup
    private lateinit var model:             RegisterViewModel
    private var attempt =                   3
    private var cart:                       MutableList<Product?> = mutableListOf<Product?>()
    private var radioState:                 String = "nfc"


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_register)
        super.onCreate(savedInstanceState)
        connectionStatus        = findViewById(R.id.connectionStatus)
        articleNameInput        = findViewById(R.id.EditTextArticleName)
        articlePriceInput       = findViewById(R.id.EditNumberArticlePrice)
        adapter                 = CartRegisterAdapter(this, cart)
        cartView                = findViewById(R.id.productListView)
        totalView               = findViewById(R.id.TotalPrice)
        totalAndPayLayout       = findViewById(R.id.totalAndPay)
        paymentMode             = findViewById(R.id.radioPayment)
        connectionStatus.text   = "connection IN PROGRESS"
        cartView.adapter        = adapter
        paymentMode.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            Toast.makeText(applicationContext," On checked change : ${radio.text}",Toast.LENGTH_SHORT).show()
            radioState = radio.text.toString()
        }
        model = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        updateCart(model.getAllProduct())
    }

    fun addProduct(view: View) {
        val name        = articleNameInput.text.toString()
        val price       = articlePriceInput.text.toString()
        val newProduct  = Product(-1, name, price)

        updateCart(model.createProduct(newProduct))
    }

    fun removeProduct(view: View) {
        val np              = getNameAndPriceFromArticleView(view) ?: return
        val copyProducts    = cart.toList()

        for (product in copyProducts) {
            if (product!!.name == np.first && product.price == np.second) {
                updateCart(model.deleteProduct(product.id_product))
                break
            }
        }
    }

    fun paymentProceed(view: View) {
        val intent = Intent(applicationContext, BillActivity::class.java)

        intent.putExtra("mode", radioState)
        startActivity(intent)
    }

    private fun getNameAndPriceFromArticleView(view: View): Pair<String, String>? {
        val parent = view.parent
        var nameView: View
        var priceView: View

        if (parent is ConstraintLayout) {
            nameView    = parent.getViewById(R.id.articleName) ?: return null
            priceView   = parent.getViewById(R.id.articlePrice) ?: return null
            nameView as TextView
            priceView as TextView

            return Pair(nameView.text.toString(), priceView.text.toString())
        }
        return null
    }

    private fun updateCart(newList: LiveData<MutableList<Product?>>) {
        progressBar.visibility = View.VISIBLE
        newList.observe(this,
            Observer<MutableList<Product?>> { products ->
                progressBar.visibility = View.INVISIBLE
                if (products != null) {
                    attempt = 3
                    connectionStatus.text = "connection ESTABLISHED"
                    cart.clear()
                    for (product in products) {
                        println(product?.name)
                        cart.add(product)
                    }
                    adapter.notifyDataSetChanged()
                    if (cart.isEmpty()) {
                        totalView.visibility = View.INVISIBLE
                        totalAndPayLayout.visibility = View.INVISIBLE
                    } else {
                        updateTotal()
                        totalView.visibility = View.VISIBLE
                        totalAndPayLayout.visibility = View.VISIBLE
                    }
                } else {
                    connectionStatus.text = "connection REFUSED"
                    if (attempt == 0) {
                        Toast.makeText(this, "Sorry connection server refused, back to authentication proceed...", Toast.LENGTH_SHORT).show()
                        backToAuthentication()
                    } else {
                        attempt = attempt.minus(1)
                        Toast.makeText(this, "Start new connection, left ".plus(attempt).plus(" attempt."), Toast.LENGTH_SHORT).show()
                        updateTotal()
                    }
                }
            })
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
                }
            })
    }

    private fun backToAuthentication() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}