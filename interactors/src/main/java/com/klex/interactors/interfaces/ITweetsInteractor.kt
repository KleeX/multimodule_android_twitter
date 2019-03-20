package com.klex.interactors.interfaces

import com.klex.interactors.Tweet
import com.klex.interactors.TweetPending
import io.reactivex.Observable
import io.reactivex.Single

interface ITweetsInteractor {
    val tweets: Single<List<Tweet>>
    fun pushTweet(textContent: String = "", picture: String = ""): Single<Tweet>
    fun pendingTweet(textContent: String = "", picture: String = "")
    fun observePendingTweet(): Observable<TweetPending>
}