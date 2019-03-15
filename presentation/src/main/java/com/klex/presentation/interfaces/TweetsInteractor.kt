package com.klex.presentation.interfaces

import com.klex.presentation.Tweet
import com.klex.presentation.TweetPending
import io.reactivex.Observable
import io.reactivex.Single

interface TweetsInteractor {
    val tweets: Single<List<Tweet>>
    fun pushTweet(textContent: String = "", picture: String = ""): Single<Tweet>
    fun observePendingTweet(): Observable<TweetPending>
    fun pendingTweet(textContent: String = "", picture: String = "")
}