package com.klex.twitter.implementations

import com.klex.twitter.TweetResponse
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Media
import com.twitter.sdk.android.core.models.Tweet
import io.reactivex.Single
import io.reactivex.SingleEmitter
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File


class TwitterTimeLine {

    val singleTweets: Single<List<TweetResponse>>
        get() = Single.create<List<TweetResponse>> { emitter ->
            TwitterCore.getInstance()
                .apiClient
                .statusesService
                .homeTimeline(
                    20,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
                ).enqueue(object : Callback<List<Tweet>>() {
                    override fun success(result: Result<List<Tweet>>?) {
                        when {
                            result == null ->
                                emitter.onError(Throwable("Something were wrong :("))
                            !result.response.isSuccessful ->
                                emitter.onError(Throwable(result.response.message()))
                            else ->
                                emitter.tweetsSuccessResponse(result.data)
                        }
                    }

                    override fun failure(exception: TwitterException?) {
                        emitter.tryOnError(Throwable(exception))
                    }
                })
        }

    private fun SingleEmitter<List<TweetResponse>>.tweetsSuccessResponse(tweets: List<Tweet>) {
        val tweetsList = mutableListOf<TweetResponse>()
        tweets.forEach { tweet ->
            with(tweet) {
                tweetsList.add(
                    TweetResponse(
                        username = user.name,
                        nickname = user.screenName,
                        userAvatar = user.profileImageUrlHttps,
                        textContent = text,
                        pictures = entities.media.map { it.mediaUrlHttps },
                        created = createdAt
                    )
                )
            }
        }
        onSuccess(tweetsList)
    }

    fun pushTweet(textContent: String = "", picture: String = ""): Single<TweetResponse> =
        Single.create<TweetResponse> { emitter ->
            if (picture.isEmpty()) {
                emitter.uploadTweet(textContent, null)
            } else TwitterCore.getInstance()
                .apiClient
                .mediaService
                .upload(
                    RequestBody.create(
                        MediaType.parse("image/jpg"),
                        File(picture)
                    ), null, null
                ).enqueue(object : Callback<Media>() {
                    override fun success(result: Result<Media>?) {
                        when {
                            result == null ->
                                emitter.onError(Throwable("Something were wrong :("))
                            !result.response.isSuccessful ->
                                emitter.onError(Throwable(result.response.message()))
                            else -> emitter.uploadTweet(textContent, result.data)
                        }
                    }

                    override fun failure(exception: TwitterException?) {
                        emitter.onError(Throwable(exception))
                    }
                })
        }

    private fun SingleEmitter<TweetResponse>.uploadTweet(text: String, media: Media?) {
        TwitterCore.getInstance()
            .apiClient
            .statusesService
            .update(
                text,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                media?.mediaIdString
            ).enqueue(object : Callback<Tweet>() {
                override fun success(result: Result<Tweet>?) {
                    when {
                        result == null ->
                            onError(Throwable("Something were wrong :("))
                        !result.response.isSuccessful ->
                            onError(Throwable(result.response.message()))
                        else -> onSuccess(with(result.data) {
                            TweetResponse(
                                username = user.name,
                                nickname = user.screenName,
                                userAvatar = user.profileImageUrlHttps,
                                textContent = text,
                                pictures = entities.media.map { it.mediaUrlHttps },
                                created = createdAt
                            )
                        })
                    }
                }

                override fun failure(exception: TwitterException?) {
                    onError(Throwable(exception))
                }
            })
    }
}