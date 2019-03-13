package com.klex.ui.activities

import android.os.Bundle
import com.klex.ui.R
import com.klex.ui.mvpx.MvpXActivity

class MainActivity : MvpXActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}