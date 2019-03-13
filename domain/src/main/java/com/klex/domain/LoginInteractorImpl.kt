package com.klex.domain

import com.klex.domain.repositories.TwitterLoginRepository

class LoginInteractorImpl(private val twitterLogin: TwitterLoginRepository) {
    fun authenticate() = twitterLogin.authenticate()
    fun logout() = twitterLogin.logout()
}