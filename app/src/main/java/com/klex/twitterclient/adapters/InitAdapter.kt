package com.klex.twitterclient.adapters

import com.klex.interactors.interfaces.IInitInteractor
import com.klex.presentation.interfaces.InitInteractor

class InitAdapter(private val initInteractorImpl: IInitInteractor) : InitInteractor {
    override val isAuthenticated: Boolean
        get() = initInteractorImpl.isAuthenticated

    override fun reinitTwitter() = initInteractorImpl.reinitTwitter()
}