package com.klex.twitterclient.adapters

import com.klex.interactors.interfaces.ILoginInteractor
import com.klex.presentation.interfaces.LoginInteractor

class LoginAdapter(private val loginInteractorImpl: ILoginInteractor) : LoginInteractor {

    override fun authenticate() = loginInteractorImpl.authenticate()

    override fun logout() = loginInteractorImpl.logout()
}