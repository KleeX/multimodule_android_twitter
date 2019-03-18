package com.klex.presentation.compose

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.klex.presentation.interfaces.TweetsInteractor
import javax.inject.Inject

@InjectViewState
class ComposePresenter @Inject constructor() : MvpPresenter<ComposeView>() {

    @Inject
    lateinit var tweetsInteractor: TweetsInteractor

    var currentPhotoPath: String? = null
    var selectedFilePath: String? = null
        set(value) {
            if (value != null) viewState.setPicture(value)
            field = value
        }
    var functionCompletePermissions: (() -> Unit)? = null
    var text = ""

    fun takePhoto() = viewState.openCamera()
    fun searchPhoto() = viewState.openGallery()

    fun pendingPost() {
        if (text.isNotEmpty() || selectedFilePath?.isNotEmpty() == true) {
            tweetsInteractor.pendingTweet(text, selectedFilePath ?: "")
            viewState.pendingSuccess()
        } else {
            viewState.validationError()
        }
    }
}