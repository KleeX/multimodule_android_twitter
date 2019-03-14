package com.klex.domain

import com.klex.domain.repositories.LoginRepository

class LoginInteractorImpl(private val login: LoginRepository) {
    fun authenticate() = login.authenticate()
    fun logout() = login.logout()
}