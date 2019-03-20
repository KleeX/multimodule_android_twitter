package com.klex.twitterclient.adapters

import com.klex.interactors.interfaces.ITweetsInteractor
import com.klex.presentation.Tweet
import com.klex.presentation.TweetPending
import com.klex.presentation.interfaces.TweetsInteractor
import io.reactivex.Observable
import io.reactivex.Single

class TweetsAdapter(private var tweetsInteractorImpl: ITweetsInteractor) : TweetsInteractor {
    override val tweets: Single<List<Tweet>>
        get() = tweetsInteractorImpl.tweets
            .map {
                val tweets = mutableListOf<Tweet>()
                it.forEach { tweet ->
                    with(tweet) {
                        tweets.add(
                            Tweet(username, nickname, userAvatar, textContent, pictureUrl, created)
                        )
                    }
                }
                return@map tweets
            }

    override fun pushTweet(textContent: String, picture: String): Single<Tweet> =
        tweetsInteractorImpl.pushTweet(textContent, picture)
            .map {
                with(it) {
                    Tweet(username, nickname, userAvatar, it.textContent, pictureUrl, created)
                }
            }

    override fun pendingTweet(textContent: String, picture: String) =
        tweetsInteractorImpl.pendingTweet(textContent, picture)

    override fun observePendingTweet(): Observable<TweetPending> =
        tweetsInteractorImpl.observePendingTweet()
            .map { TweetPending(it.text, it.picturePath) }
}