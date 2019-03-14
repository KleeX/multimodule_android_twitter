package com.klex.twitterclient.repositories

import com.klex.domain.repositories.InitRepository
import com.klex.twitter.implementations.TwitterInit

class InitRepoImpl(private val twitterInit: TwitterInit) : InitRepository {

    override val isAuthenticated: Boolean
        get() = twitterInit.isAuthenticated

    override fun reinitTwitter() = twitterInit.reinitTwitter()
}