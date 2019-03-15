package com.klex.domain

import com.klex.domain.repositories.TweetsRepository
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single

class TweetsInteractorImpl(
    private val tweetsRepository: TweetsRepository,
    private val observeScheduler: Scheduler,
    private val subscribeScheduler: Scheduler
) {
    val tweets: Single<List<Tweet>>
        get() = tweetsRepository.tweets
            .map { tweets ->
                tweets.asSequence()
                    .sortedByDescending { it.created }
                    .toList()
            }
            .subscribeOn(subscribeScheduler)
            .observeOn(observeScheduler)

    fun pushTweet(textContent: String = "", picture: String = ""): Single<Tweet> =
        tweetsRepository.pushTweet(textContent, picture)
            .subscribeOn(subscribeScheduler)
            .observeOn(observeScheduler)

    fun pendingTweet(textContent: String = "", picture: String = "") =
        tweetsRepository.pendingTweet(textContent, picture)

    fun observePendingTweet(): Observable<TweetPending> = tweetsRepository.observePendingTweet()
        .subscribeOn(subscribeScheduler)
        .observeOn(observeScheduler)
}