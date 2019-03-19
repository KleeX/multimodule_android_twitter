package com.klex.domain

import com.klex.domain.repositories.TweetsRepository
import io.reactivex.Observable
import io.reactivex.Single

class TweetsInteractorImpl(
    private val tweetsRepository: TweetsRepository,
    private val scheduler: SchedulerDataSource
) {
    val tweets: Single<List<Tweet>>
        get() = tweetsRepository.tweets
            .map { tweets ->
                tweets.asSequence()
                    .sortedByDescending { it.created }
                    .toList()
            }
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.main())

    fun pushTweet(textContent: String = "", picture: String = ""): Single<Tweet> =
        tweetsRepository.pushTweet(textContent, picture)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.main())

    fun pendingTweet(textContent: String = "", picture: String = "") =
        tweetsRepository.pendingTweet(textContent, picture)

    fun observePendingTweet(): Observable<TweetPending> = tweetsRepository.observePendingTweet()
        .subscribeOn(scheduler.io())
        .observeOn(scheduler.main())
}