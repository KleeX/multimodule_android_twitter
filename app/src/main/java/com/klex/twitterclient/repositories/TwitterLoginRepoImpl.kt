package com.klex.twitterclient.repositories

import com.klex.domain.repositories.TwitterLoginRepository
import com.klex.twitter.implementations.TwitterLogin

class TwitterLoginRepoImpl(private val twitterLogin: TwitterLogin) : TwitterLoginRepository {
    override fun authenticate() = twitterLogin.authenticate()
    override fun logout() = twitterLogin.logout()
}