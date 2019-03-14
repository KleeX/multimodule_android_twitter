package com.klex.domain.datasources

interface TwitterInitDataSource {
    val isAuthenticated: Boolean
    fun reinitTwitter()
}