package com.klex.ui.compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.klex.presentation.compose.ComposePresenter
import com.klex.presentation.compose.ComposeView
import com.klex.ui.R
import com.klex.ui.mvpx.MvpXFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ComposeFragment : MvpXFragment(), ComposeView {

    @Inject
    @InjectPresenter
    lateinit var presenter: ComposePresenter

    @ProvidePresenter
    fun providePresenter(): ComposePresenter = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_compose, container, false)

    override fun openGallery() {

    }

    override fun openCamera() {

    }
}