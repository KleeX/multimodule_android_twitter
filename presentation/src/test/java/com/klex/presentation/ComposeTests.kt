package com.klex.presentation

import com.arellomobile.mvp.viewstate.MvpViewState
import com.klex.presentation.compose.ComposePresenter
import com.klex.presentation.compose.ComposeView
import com.klex.presentation.interfaces.TweetsInteractor
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

class ComposeTests {
    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var tweetsInteractor: TweetsInteractor

    @Mock
    lateinit var composeViewState: ComposeViewState

    @InjectMocks
    lateinit var composePresenter: ComposePresenter

    @Before
    fun setUp() {
        composePresenter.setViewState(composeViewState)
    }

    @Test
    fun tweetsTests() {
        checkException(composePresenter::searchPhoto)
        Mockito.verify(composeViewState, Mockito.times(1)).openGallery()

        checkException(composePresenter::takePhoto)
        verify(composeViewState, times(1)).openCamera()

        composePresenter.text = ""
        composePresenter.selectedFilePath = ""
        checkException(composePresenter::pendingPost)
        verify(composeViewState, times(1)).validationError()

        composePresenter.text = "text"
        composePresenter.selectedFilePath = ""
        checkException(composePresenter::pendingPost)
        verify(composeViewState, times(1)).pendingSuccess()

        composePresenter.text = ""
        composePresenter.selectedFilePath = "picture"
        checkException(composePresenter::pendingPost)
        verify(composeViewState, times(2)).pendingSuccess()

        composePresenter.text = "text"
        composePresenter.selectedFilePath = "picture"
        checkException(composePresenter::pendingPost)
        verify(composeViewState, times(3)).pendingSuccess()
    }

    open class ComposeViewState : MvpViewState<ComposeView>(), ComposeView {
        override fun openGallery() = Unit
        override fun openCamera() = Unit
        override fun pendingSuccess() = Unit
        override fun validationError() = Unit
        override fun setPicture(path: String) = Unit
    }
}