package com.klex.presentation.compose

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import javax.inject.Inject

@InjectViewState
class ComposePresenter @Inject constructor() : MvpPresenter<ComposeView>()