package com.klex.twitterclient.sources

import com.klex.domain.datasources.TwitterInitDataSource
import com.klex.twitter.implementations.TwitterInit

class TwitterInitSource(private val twitterInit: TwitterInit) : TwitterInitDataSource {

    override val isAuthenticated: Boolean
        get() = twitterInit.isAuthenticated

    override fun reinitTwitter() = twitterInit.reinitTwitter()
}