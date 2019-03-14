package com.klex.domain

import com.klex.domain.datasources.TwitterInitDataSource

class InitInteractorImpl(private val twitterInit: TwitterInitDataSource) {

    val isAuthenticated: Boolean
        get() = twitterInit.isAuthenticated

    fun reinitTwitter() = twitterInit.reinitTwitter()
}