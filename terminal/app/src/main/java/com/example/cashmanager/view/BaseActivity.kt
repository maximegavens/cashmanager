package com.example.cashmanager.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.cashmanager.R

open class BaseActivity : AppCompatActivity() {

    protected lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressBar = findViewById(R.id.progress_bar)
        progressBar.visibility  = View.INVISIBLE
    }
}