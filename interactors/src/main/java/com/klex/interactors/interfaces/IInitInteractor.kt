package com.klex.interactors.interfaces

interface IInitInteractor {
    val isAuthenticated: Boolean
    fun reinitTwitter()
}