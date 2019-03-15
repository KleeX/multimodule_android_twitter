package com.klex.domain.repositories

import com.klex.domain.Tweet
import io.reactivex.Single

interface TweetsRepository {
    val tweets: Single<List<Tweet>>
    fun pushTweet(textContent: String = "", picture: String = ""): Single<Tweet>
}