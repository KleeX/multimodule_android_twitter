package com.klex.twitter.implementations

import android.content.Context
import android.content.Intent
import com.klex.twitter.TweetResponse
import com.klex.twitter.fake_activities.FakeTweetActivity
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet
import io.reactivex.Single
import io.reactivex.SingleEmitter


class TwitterTimeLine(private val context: Context) {

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
                            result.response.isSuccessful ->
                                emitter.onError(Throwable(result.response.message()))
                            else ->
                                emitter.tweetsSuccessResponse(result.data)
                        }
                    }

                    override fun failure(exception: TwitterException?) {
                        emitter.onError(Throwable(exception))
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
                        userAvatar = user.profileImageUrl,
                        textContent = text,
                        pictures = entities.media.map { it.mediaUrl }
                    )
                )
            }
        }
        onSuccess(tweetsList)
    }

    fun pushTweet(textContent: String = "", picture: String = "") {
        if (textContent.isEmpty() && picture.isEmpty()) throw Throwable("Both fields could not be empty!")
        val intent = Intent(context, FakeTweetActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra(FakeTweetActivity.TWEET_TEXT, textContent)
        intent.putExtra(FakeTweetActivity.TWEET_PICTURE, picture)
        context.startActivity(intent)
    }
}