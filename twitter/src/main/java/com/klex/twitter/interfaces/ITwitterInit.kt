package com.klex.twitter.interfaces

interface ITwitterInit {
    val isAuthenticated: Boolean
    fun reinitTwitter()
}