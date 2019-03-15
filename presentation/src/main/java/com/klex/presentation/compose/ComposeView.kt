package com.klex.presentation.compose

import com.arellomobile.mvp.MvpView

interface ComposeView : MvpView {
    fun openGallery()
    fun openCamera()
}