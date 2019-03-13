package com.klex.domain

import com.klex.domain.repositories.TwitterInitRepository

class InitInteractorImpl(private val twitterInit: TwitterInitRepository) {

    val isAuthenticated: Boolean
        get() = twitterInit.isAuthenticated

    fun reinitTwitter() = twitterInit.reinitTwitter()
}