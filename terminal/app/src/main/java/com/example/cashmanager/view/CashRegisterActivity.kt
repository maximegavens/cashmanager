package com.example.cashmanager.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.cashmanager.R
import kotlinx.android.synthetic.main.activity_cash_register.*

class CashRegisterActivity : AppCompatActivity() {
    private lateinit var mainView: TextView
    private var SERVER_ID: String = "id"
    private var SERVER_PASSWORD: String = "password"
    private var SERVER_STATUS: String = "status"
    private var SERVER_LAST_CONNECTION: String = "connection"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash_register)

/*
        val colors = listOf("blue", "red", "yellow")
*/
        val intent = getIntent()
        val id = intent.getStringExtra(SERVER_ID)
        val password = intent.getStringExtra(SERVER_PASSWORD)
        val status = intent.getStringExtra(SERVER_STATUS)
        val last_connection = intent.getStringExtra(SERVER_LAST_CONNECTION)
        val concat_result = id + " " + status + " " + password + " " + last_connection

        mainView = findViewById(R.id.activity2)
        mainView.setText(concat_result)

        /*val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_expandable_list_item_1,
            colors
        )

        // Finally, data bind the list view object with adapter
        list_view.adapter = adapter;

        // Set an item click listener for ListView
        list_view.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // Get the selected item text from ListView
            val selectedItem = parent.getItemAtPosition(position) as String

            // Display the selected item text on TextView
            text_view.text = "Your favorite color: $selectedItem"
        }*/
    }
}
