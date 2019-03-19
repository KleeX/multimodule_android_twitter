package com.klex.domain

import com.klex.domain.repositories.InitRepository
import com.klex.domain.repositories.LoginRepository
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class AuthTests {

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var initRepository: InitRepository
    @Mock
    lateinit var loginRepository: LoginRepository

    @InjectMocks
    lateinit var loginInteractor: LoginInteractorImpl

    @InjectMocks
    lateinit var initInteractor: InitInteractorImpl

    @Test
    fun initializationTests() {
        Mockito.`when`(initRepository.isAuthenticated)
            .thenReturn(true)
        assertTrue(initInteractor.isAuthenticated)

        Mockito.`when`(initRepository.isAuthenticated)
            .thenReturn(false)
        assertTrue(!initInteractor.isAuthenticated)

        checkException(initRepository::reinitTwitter)
    }

    @Test
    fun authTests() {
        checkException(loginInteractor::authenticate)
        checkException(loginInteractor::logout)
    }
}