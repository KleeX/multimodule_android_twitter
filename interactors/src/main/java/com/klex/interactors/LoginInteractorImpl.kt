package com.klex.interactors

import com.klex.interactors.interfaces.ILoginInteractor
import com.klex.interactors.repositories.LoginRepository

class LoginInteractorImpl(private val login: LoginRepository) : ILoginInteractor {
    override fun authenticate() = login.authenticate()
    override fun logout() = login.logout()
}