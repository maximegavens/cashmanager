package com.example.cashmanager.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.cashmanager.R
import com.example.cashmanager.model.ProductView
import kotlinx.android.synthetic.main.listview_product.view.*

class ProductAdapter(context: Context, products: MutableList<ProductView?>)
    : ArrayAdapter<ProductView>(context, 0, products) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rootView = convertView?: LayoutInflater.from(context).inflate(R.layout.listview_product, parent, false)
        val currentArticle = getItem(position)

        rootView.productName.text = currentArticle!!.label
        rootView.productPrice.text = currentArticle!!.price
        rootView.setBackgroundColor(Color.parseColor(currentArticle.color))

        return rootView
    }
}