package com.klex.presentation.interfaces

interface InitInteractor {
    val isAuthenticated: Boolean
    fun reinitTwitter()
}