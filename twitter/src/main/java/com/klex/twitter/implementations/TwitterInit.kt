package com.klex.twitter.implementations

import android.content.Context
import android.util.Log
import com.klex.twitter.BuildConfig
import com.klex.twitter.interfaces.TwitterInitSource
import com.twitter.sdk.android.core.*

class TwitterInit(private val context: Context) : TwitterInitSource {

    override val isAuthenticated: Boolean
        get() = TwitterCore.getInstance().sessionManager.activeSession != null

    override fun reinitTwitter() = com.klex.twitter.implementations.initTwitter(context)
}

fun initTwitter(context: Context) {
    val config = TwitterConfig.Builder(context)
        .logger(DefaultLogger(Log.DEBUG))
        .twitterAuthConfig(
            TwitterAuthConfig(
                BuildConfig.CONSUMER_KEY,
                BuildConfig.CONSUMER_SECRET
            )
        )
        .debug(BuildConfig.DEBUG)
        .build()
    Twitter.initialize(config)
}