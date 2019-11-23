package com.example.cashmanager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.cashmanager.R
import com.example.cashmanager.model.Product
import kotlinx.android.synthetic.main.listview_register.view.*

class CartRegisterAdapter(context: Context, products: MutableList<Product?>)
    : ArrayAdapter<Product>(context, 0, products) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rootView = convertView?: LayoutInflater.from(context).inflate(R.layout.listview_register, parent, false)
        val currentArticle = getItem(position)

        rootView.articleName.text = currentArticle!!.name
        rootView.articlePrice.text = currentArticle.price

        return rootView
    }
}