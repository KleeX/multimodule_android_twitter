package com.klex.presentation.splash

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.klex.presentation.interfaces.InitInteractor
import javax.inject.Inject

@InjectViewState
class SplashPresenter @Inject constructor() : MvpPresenter<SplashView>() {

    @Inject
    internal lateinit var initInteractor: InitInteractor

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        initInteractor.reinitTwitter()
    }

    fun checkAuth() {
        if (initInteractor.isAuthenticated)
            viewState.userLogged()
        else
            viewState.userShouldLogin()
    }
}