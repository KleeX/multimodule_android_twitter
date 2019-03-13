package com.klex.twitterclient.repositories

import com.klex.domain.repositories.TwitterLoginRepository
import com.klex.twitter.interfaces.TwitterLoginSource

class TwitterLoginRepoImpl(private val twitterLogin: TwitterLoginSource) : TwitterLoginRepository {
    override fun authenticate() = twitterLogin.authenticate()
    override fun logout() = twitterLogin.logout()
}