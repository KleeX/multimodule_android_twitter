package com.klex.twitterclient.repositories

import com.klex.interactors.repositories.InitRepository
import com.klex.twitter.interfaces.ITwitterInit

class InitRepoImpl(private val twitterInit: ITwitterInit) : InitRepository {

    override val isAuthenticated: Boolean
        get() = twitterInit.isAuthenticated

    override fun reinitTwitter() = twitterInit.reinitTwitter()
}