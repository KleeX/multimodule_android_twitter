package com.klex.twitterclient.repositories

import android.annotation.SuppressLint
import com.klex.interactors.Tweet
import com.klex.interactors.TweetPending
import com.klex.interactors.repositories.TweetsRepository
import com.klex.storage.interfaces.IPendingTweet
import com.klex.twitter.interfaces.ITwitterTimeLine
import io.reactivex.Observable
import io.reactivex.Single
import java.text.SimpleDateFormat

class TweetsRepoImpl(
    private val twitterTimeLine: ITwitterTimeLine,
    private val pendingTweet: IPendingTweet
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
                        textContent = it.textContent,
                        pictureUrl = if (pictures.isNotEmpty()) pictures.first() else "",
                        created = dateFormat.parse(created).time
                    )
                }
            }
}