package com.klex.twitterclient.repositories

import com.klex.domain.repositories.TwitterInitRepository
import com.klex.twitter.implementations.TwitterInit

class TwitterInitRepoImpl(private val twitterInit: TwitterInit) : TwitterInitRepository {

    override val isAuthenticated: Boolean
        get() = twitterInit.isAuthenticated

    override fun reinitTwitter() = twitterInit.reinitTwitter()
}