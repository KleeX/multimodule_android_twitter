package com.klex.twitterclient.adapters

import com.klex.domain.InitInteractorImpl
import com.klex.presentation.interfaces.InitInteractor

class InitAdapter(private val initInteractorImpl: InitInteractorImpl) : InitInteractor {
    override val isAuthenticated: Boolean
        get() = initInteractorImpl.isAuthenticated

    override fun reinitTwitter() = initInteractorImpl.reinitTwitter()
}