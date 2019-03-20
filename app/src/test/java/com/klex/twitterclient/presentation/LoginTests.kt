package com.klex.twitterclient.presentation

import com.arellomobile.mvp.viewstate.MvpViewState
import com.klex.presentation.interfaces.InitInteractor
import com.klex.presentation.interfaces.LoginInteractor
import com.klex.presentation.login.LoginPresenter
import com.klex.presentation.login.LoginView
import com.klex.presentation.splash.SplashPresenter
import com.klex.presentation.splash.SplashView
import com.klex.twitterclient.checkException
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class LoginTests {

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var initInteractor: InitInteractor
    @Mock
    lateinit var loginInteractor: LoginInteractor

    @Mock
    lateinit var splashViewState: SplashViewState
    @Mock
    lateinit var loginViewState: LoginViewState

    @InjectMocks
    lateinit var splashPresenter: SplashPresenter

    @InjectMocks
    lateinit var loginPresenter: LoginPresenter

    @Before
    fun setUp() {
        splashPresenter.setViewState(splashViewState)
        loginPresenter.setViewState(loginViewState)
    }

    @Test
    fun testLogin() {
        Mockito.`when`(initInteractor.isAuthenticated).thenReturn(true)
        checkException(splashPresenter::checkAuth)
        verify(splashViewState, times(1)).userLogged()

        Mockito.`when`(initInteractor.isAuthenticated).thenReturn(false)
        checkException(splashPresenter::checkAuth)
        verify(splashViewState, times(1)).userShouldLogin()

        Mockito.`when`(initInteractor.isAuthenticated).thenReturn(true)
        checkException(loginPresenter::checkLogged)
        verify(loginViewState, times(1)).loginSuccess()

        Mockito.`when`(initInteractor.isAuthenticated).thenReturn(false)
        checkException(loginPresenter::checkLogged)
        verify(loginViewState, times(1)).loginSuccess()

        checkException(loginPresenter::login)
        verify(loginInteractor, times(1)).authenticate()
    }

    open class SplashViewState : MvpViewState<SplashView>(), SplashView {
        override fun userShouldLogin() = Unit
        override fun userLogged() = Unit
    }

    open class LoginViewState : MvpViewState<LoginView>(), LoginView {
        override fun loginSuccess() = Unit
    }
}

