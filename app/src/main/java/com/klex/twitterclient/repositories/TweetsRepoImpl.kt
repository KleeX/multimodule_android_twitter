package com.klex.twitterclient.repositories

import android.annotation.SuppressLint
import com.klex.domain.Tweet
import com.klex.domain.TweetPending
import com.klex.domain.repositories.TweetsRepository
import com.klex.storage.PendingTweet
import com.klex.twitter.implementations.TwitterTimeLine
import io.reactivex.Observable
import io.reactivex.Single
import java.text.SimpleDateFormat

class TweetsRepoImpl(
    private val twitterTimeLine: TwitterTimeLine,
    private val pendingTweet: PendingTweet
) : TweetsRepository {

    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy")

    override val tweets: Single<List<Tweet>>
        get() = twitterTimeLine.singleTweets
            .map {
                val tweets = mutableListOf<Tweet>()
                it.forEach { tweet ->
                    with(tweet) {
                        tweets.add(
                            Tweet(
                                username = username,
                                nickname = nickname,
                                userAvatar = userAvatar,
                                textContent = textContent,
                                pictureUrl = if (pictures.isNotEmpty()) pictures.first() else "",
                                created = dateFormat.parse(created).time
                            )
                        )
                    }
                }
                return@map tweets
            }

    override fun pendingTweet(textContent: String, picture: String) =
        pendingTweet.putPendingTweet(textContent, picture)

    override fun observePendingTweet(): Observable<TweetPending> =
        pendingTweet.observePendingTweet()
            .map { TweetPending(it.text, it.picturePath) }

    override fun pushTweet(textContent: String, picture: String): Single<Tweet> =
        twitterTimeLine.pushTweet(textContent, picture)
            .map {
                with(it) {
                    Tweet(
                        username = username,
                        nickname = nickname,
                        userAvatar = userAvatar,
                        textContent = textContent,
                        pictureUrl = if (pictures.isNotEmpty()) pictures.first() else "",
                        created = dateFormat.parse(created).time
                    )
                }
            }
}