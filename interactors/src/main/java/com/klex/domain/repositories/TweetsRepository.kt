package com.klex.domain.repositories

import com.klex.domain.Tweet
import com.klex.domain.TweetPending
import io.reactivex.Observable
import io.reactivex.Single

interface TweetsRepository {
    val tweets: Single<List<Tweet>>
    fun pushTweet(textContent: String = "", picture: String = ""): Single<Tweet>
    fun pendingTweet(textContent: String = "", picture: String = "")
    fun observePendingTweet(): Observable<TweetPending>
}