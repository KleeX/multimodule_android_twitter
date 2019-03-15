package com.klex.presentation.tweets

import com.arellomobile.mvp.MvpView

interface TweetsView : MvpView {
    fun showTweets()
    fun openCreation()
    fun onErrorLoadingTweets()
    fun showLoading()
    fun hideLoading()
}