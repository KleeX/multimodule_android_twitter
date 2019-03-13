package com.klex.twitterclient.delegates

import com.klex.domain.InitInteractorImpl
import com.klex.presentation.interfaces.InitInteractor

class InitDelegate(private val initInteractorImpl: InitInteractorImpl) : InitInteractor {
    override val isAuthenticated: Boolean
        get() = initInteractorImpl.isAuthenticated

    override fun reinitTwitter() = initInteractorImpl.reinitTwitter()
}