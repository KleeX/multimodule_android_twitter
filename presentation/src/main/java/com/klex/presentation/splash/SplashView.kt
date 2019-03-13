package com.klex.presentation.splash

import com.arellomobile.mvp.MvpView

interface SplashView : MvpView {
    fun userShouldLogin()
    fun userLogged()
}