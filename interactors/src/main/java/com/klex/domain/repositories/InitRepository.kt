package com.klex.domain.repositories

interface InitRepository {
    val isAuthenticated: Boolean
    fun reinitTwitter()
}