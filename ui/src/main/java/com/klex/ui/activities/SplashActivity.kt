package com.klex.ui.activities

import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.klex.presentation.splash.SplashPresenter
import com.klex.presentation.splash.SplashView
import com.klex.ui.mvpx.MvpXActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

class SplashActivity : MvpXActivity(), SplashView {

    @Inject
    @InjectPresenter
    lateinit var presenter: SplashPresenter

    @ProvidePresenter
    fun providePresenter(): SplashPresenter = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        presenter.checkAuth()
    }

    override fun userShouldLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun userLogged() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}
