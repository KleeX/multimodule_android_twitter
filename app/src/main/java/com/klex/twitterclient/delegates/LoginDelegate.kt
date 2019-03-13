package com.klex.twitterclient.delegates

import com.klex.domain.LoginInteractorImpl
import com.klex.presentation.interfaces.LoginInteractor

class LoginDelegate(private val loginInteractorImpl: LoginInteractorImpl) : LoginInteractor {

    override fun authenticate() = loginInteractorImpl.authenticate()

    override fun logout() = loginInteractorImpl.logout()
}