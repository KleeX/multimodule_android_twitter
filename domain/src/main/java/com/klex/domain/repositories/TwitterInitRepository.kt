package com.klex.domain.repositories

interface TwitterInitRepository {
    val isAuthenticated: Boolean
    fun reinitTwitter()
}