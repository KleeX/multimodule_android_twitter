package com.klex.twitterclient.repositories

import com.klex.domain.repositories.TwitterInitRepository
import com.klex.twitter.interfaces.TwitterInitSource

class TwitterInitRepoImpl(private val twitterInit: TwitterInitSource) : TwitterInitRepository {

    override val isAuthenticated: Boolean
        get() = twitterInit.isAuthenticated

    override fun reinitTwitter() = twitterInit.reinitTwitter()
}