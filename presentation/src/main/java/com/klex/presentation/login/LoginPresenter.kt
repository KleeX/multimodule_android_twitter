package com.klex.presentation.login

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.klex.presentation.interfaces.InitInteractor
import com.klex.presentation.interfaces.LoginInteractor
import javax.inject.Inject

@InjectViewState
class LoginPresenter @Inject constructor() : MvpPresenter<LoginView>() {

    @Inject
    internal lateinit var loginInteractor: LoginInteractor

    @Inject
    internal lateinit var initInteractor: InitInteractor

    fun login() = loginInteractor.authenticate()

    fun checkLogged() {
        if (initInteractor.isAuthenticated)
            viewState.loginSuccess()
    }
}