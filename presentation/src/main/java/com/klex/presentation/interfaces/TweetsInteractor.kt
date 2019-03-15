package com.klex.presentation.interfaces

import com.klex.presentation.Tweet
import io.reactivex.Single

interface TweetsInteractor {
    val tweets: Single<List<Tweet>>
    fun pushTweet(textContent: String = "", picture: String = ""): Single<Tweet>
}