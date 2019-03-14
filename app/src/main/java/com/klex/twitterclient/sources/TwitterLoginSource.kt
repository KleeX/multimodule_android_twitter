package com.klex.twitterclient.sources

import com.klex.domain.datasources.TwitterLoginDataSource
import com.klex.twitter.implementations.TwitterLogin

class TwitterLoginSource(private val twitterLogin: TwitterLogin) : TwitterLoginDataSource {
    override fun authenticate() = twitterLogin.authenticate()
    override fun logout() = twitterLogin.logout()
}