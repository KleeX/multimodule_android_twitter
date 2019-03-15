package com.klex.domain

import com.klex.domain.repositories.TweetsRepository
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

    fun pushTweet(textContent: String = "", picture: String = "") =
        tweetsRepository.pushTweet(textContent, picture)
}