package com.klex.twitterclient.repositories

import com.klex.interactors.repositories.LoginRepository
import com.klex.twitter.interfaces.ITwitterLogin

class LoginRepoImpl(private val twitterLogin: ITwitterLogin) : LoginRepository {
    override fun authenticate() = twitterLogin.authenticate()
    override fun logout() = twitterLogin.logout()
}