package com.klex.twitterclient.adapters

import com.klex.domain.LoginInteractorImpl
import com.klex.presentation.interfaces.LoginInteractor

class LoginAdapter(private val loginInteractorImpl: LoginInteractorImpl) : LoginInteractor {

    override fun authenticate() = loginInteractorImpl.authenticate()

    override fun logout() = loginInteractorImpl.logout()
}