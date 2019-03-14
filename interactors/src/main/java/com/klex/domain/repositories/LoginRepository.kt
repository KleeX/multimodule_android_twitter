package com.klex.domain.repositories

interface LoginRepository {
    fun authenticate()
    fun logout()
}