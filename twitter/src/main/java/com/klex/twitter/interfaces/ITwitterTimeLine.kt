package com.klex.twitter.interfaces

import com.klex.twitter.TweetResponse
import io.reactivex.Single


interface ITwitterTimeLine {
    val singleTweets: Single<List<TweetResponse>>
    fun pushTweet(textContent: String = "", picture: String = ""): Single<TweetResponse>
}