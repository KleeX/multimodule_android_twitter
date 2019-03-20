package com.klex.interactors.repositories

interface LoginRepository {
    fun authenticate()
    fun logout()
}