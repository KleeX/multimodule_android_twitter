package com.klex.domain.repositories

interface TwitterLoginRepository {
    fun authenticate()
    fun logout()
}