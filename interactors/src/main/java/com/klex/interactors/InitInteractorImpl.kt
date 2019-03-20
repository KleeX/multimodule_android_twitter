package com.klex.interactors

import com.klex.interactors.interfaces.IInitInteractor
import com.klex.interactors.repositories.InitRepository

class InitInteractorImpl(private val init: InitRepository) : IInitInteractor {

    override val isAuthenticated: Boolean
        get() = init.isAuthenticated

    override fun reinitTwitter() = init.reinitTwitter()
}