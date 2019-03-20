package com.klex.interactors.repositories

interface InitRepository {
    val isAuthenticated: Boolean
    fun reinitTwitter()
}