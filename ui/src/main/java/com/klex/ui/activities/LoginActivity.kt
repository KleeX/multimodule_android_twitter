package com.klex.ui.activities

import android.os.Bundle
import com.klex.ui.R
import com.klex.ui.mvpx.MvpXActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : MvpXActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        tv_login_btn.setOnClickListener {

        }
    }
}