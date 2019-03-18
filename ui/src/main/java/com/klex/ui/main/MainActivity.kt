package com.klex.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.klex.ui.R
import com.klex.ui.hideKeyboard

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        hideKeyboard()
        super.onBackPressed()
    }
}