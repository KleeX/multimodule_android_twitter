package com.klex.domain.datasources

interface TwitterLoginDataSource {
    fun authenticate()
    fun logout()
}