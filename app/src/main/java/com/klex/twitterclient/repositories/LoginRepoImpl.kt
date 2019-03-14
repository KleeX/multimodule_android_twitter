package com.klex.twitterclient.repositories

import com.klex.domain.repositories.LoginRepository
import com.klex.twitter.implementations.TwitterLogin

class LoginRepoImpl(private val twitterLogin: TwitterLogin) : LoginRepository {
    override fun authenticate() = twitterLogin.authenticate()
    override fun logout() = twitterLogin.logout()
}