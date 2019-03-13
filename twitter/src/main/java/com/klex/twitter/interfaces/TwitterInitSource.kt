package com.klex.twitter.interfaces

interface TwitterInitSource {
    val isAuthenticated: Boolean
    fun reinitTwitter()
}