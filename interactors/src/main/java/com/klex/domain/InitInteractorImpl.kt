package com.klex.domain

import com.klex.domain.repositories.InitRepository

class InitInteractorImpl(private val init: InitRepository) {

    val isAuthenticated: Boolean
        get() = init.isAuthenticated

    fun reinitTwitter() = init.reinitTwitter()
}