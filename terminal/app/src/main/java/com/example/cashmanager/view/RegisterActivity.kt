package com.example.cashmanager.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
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

class RegisterActivity : AppCompatActivity() {

    private var             cart:           MutableList<Product?> = mutableListOf<Product?>()
    private var             total:          Double = 0.0
    private var             radioState:     String = "nfc"
    private var             ipServer:       String = "localhost"

    private lateinit var articleNameInput:  EditText
    private lateinit var articlePriceInput: EditText
    private lateinit var connectedStatus:   TextView
    private lateinit var cartView:          ListView
    private lateinit var adapter: CartRegisterAdapter
    private lateinit var totalView:         TextView
    private lateinit var totalAndPayLayout: ConstraintLayout
    private lateinit var paymentMode:       RadioGroup

    private lateinit var model:             RegisterViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val intent              = getIntent()
        val id                  = intent.getStringExtra("id") ?: "None"
        val status              = intent.getStringExtra("status") ?: "None"
        val lastConnection      = intent.getStringExtra("last connection") ?: "None"
        val concatResult        = id.plus(" ").plus(" ").plus(status).plus(" ").plus(lastConnection)

        ipServer                = intent.getStringExtra("server") ?: "localhost"

        connectedStatus     = findViewById(R.id.connectionState)
        articleNameInput    = findViewById(R.id.EditTextArticleName)
        articlePriceInput   = findViewById(R.id.EditNumberArticlePrice)
        adapter             = CartRegisterAdapter(this, cart)
        cartView            = findViewById(R.id.productListView)
        totalView           = findViewById(R.id.TotalPrice)
        totalAndPayLayout   = findViewById(R.id.totalAndPay)
        paymentMode         = findViewById(R.id.radioPayment)

        connectedStatus.text    = concatResult
        cartView.adapter        = adapter

        paymentMode.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            Toast.makeText(applicationContext," On checked change : ${radio.text}",
                Toast.LENGTH_SHORT).show()
            radioState = radio.text.toString()
        }

        model = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        updateCart(model.getAllProduct(ipServer))
    }

    fun addProduct(view: View) {
        val name        = articleNameInput.text.toString()
        val price       = articlePriceInput.text.toString()
        val newProduct  = Product(-1, name, price)

        updateCart(model.createProduct(newProduct, ipServer))
    }

    fun removeProduct(view: View) {
        val np = getNameAndPriceFromArticleView(view) ?: return
        val copyProducts = cart.toList()

        for (product in copyProducts) {
            if (product!!.name == np.first && product.price == np.second) {
                updateCart(model.deleteProduct(product.id_product, ipServer))
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
        intent.putExtra("mode", radioState)

        intent.putExtra("server", ipServer)
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

    private fun updateCart(newList: LiveData<MutableList<Product?>>) {
        newList.observe(this,
            Observer<MutableList<Product?>> { products ->
                var total = 0.0
                if (products != null) {
                    cart.clear()
                    for (product in products) {
                        println(product?.name)
                        cart.add(product)
                        total = total.plus(product!!.price.toDouble())
                    }
                    totalView.text = total.toString()
                    adapter.notifyDataSetChanged()
                    if (cart.isEmpty()) {
                        totalAndPayLayout.visibility = View.INVISIBLE
                    } else {
                        totalAndPayLayout.visibility = View.VISIBLE
                    }
                } else {
                    Toast.makeText(this, "Products is null", Toast.LENGTH_SHORT).show()
                }
            })
    }
}