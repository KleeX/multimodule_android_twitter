package com.klex.ui.login

import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.klex.presentation.login.LoginPresenter
import com.klex.presentation.login.LoginView
import com.klex.ui.R
import com.klex.ui.main.MainActivity
import com.klex.ui.mvpx.MvpXActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : MvpXActivity(), LoginView {

    @Inject
    @InjectPresenter
    lateinit var presenter: LoginPresenter

    @ProvidePresenter
    fun providePresenter(): LoginPresenter = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        tv_login_btn.setOnClickListener {
            presenter.login()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.checkLogged()
    }

    override fun loginSuccess() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}