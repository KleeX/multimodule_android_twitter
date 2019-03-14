package com.klex.domain

import com.klex.domain.datasources.TwitterLoginDataSource

class LoginInteractorImpl(private val twitterLogin: TwitterLoginDataSource) {
    fun authenticate() = twitterLogin.authenticate()
    fun logout() = twitterLogin.logout()
}