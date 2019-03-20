package com.klex.storage.interfaces

import com.klex.storage.TweetPendingEntity
import io.reactivex.Observable

interface IPendingTweet {
    fun clear()
    fun putPendingTweet(text: String = "", picturePath: String = "")
    fun observePendingTweet(): Observable<TweetPendingEntity>
}