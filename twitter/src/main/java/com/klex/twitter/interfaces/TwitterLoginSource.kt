package com.klex.twitter.interfaces

interface TwitterLoginSource {
    fun authenticate()
    fun logout()
}