package com.klex.presentation.tweets

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.klex.presentation.Time
import com.klex.presentation.TimePointer
import com.klex.presentation.Tweet
import com.klex.presentation.TweetPending
import com.klex.presentation.interfaces.TweetsInteractor
import io.reactivex.disposables.Disposable
import java.util.*
import javax.inject.Inject


@InjectViewState
class TweetsPresenter @Inject constructor() : MvpPresenter<TweetsView>() {

    @Inject
    lateinit var tweetsInteractor: TweetsInteractor

    private var tweetsDisposable: Disposable? = null
    private var tweetPendingDisposable: Disposable? = null
    private var tweetPushingDisposable: Disposable? = null
    var tweets = mutableListOf<Tweet>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showLoading()
        loadTweets()
    }

    override fun attachView(view: TweetsView?) {
        super.attachView(view)
        checkTweets()
        tweetPendingDisposable?.dispose()
        tweetPendingDisposable = tweetsInteractor.observePendingTweet()
            .subscribe({
                if (it.text.isNotEmpty() || it.picturePath.isNotEmpty())
                    pushTweet(it)
                viewState.showPendingTweet()
            }, {
                it.printStackTrace()
                viewState.hidePendingTweet()
            })
    }

    private fun pushTweet(tweet: TweetPending) {
        tweetPushingDisposable?.dispose()
        tweetPushingDisposable = tweetsInteractor.pushTweet(tweet.text, tweet.picturePath)
            .subscribe({
                tweets.add(0, it)
                viewState.notifyTweetAdded()
                tweetsInteractor.pendingTweet()
                viewState.hidePendingTweet()
            }, {
                it.printStackTrace()
                viewState.hidePendingTweet()
            })
    }

    fun checkTweets() {
        if (tweets.isEmpty()) {
            viewState.showLoading()
            loadTweets()
        }
    }

    fun loadTweets() {
        tweetsDisposable?.dispose()
        tweetsDisposable = tweetsInteractor.tweets
            .map {
                val currentTime = Date()
                it.forEach { tweet -> tweet.time = getTimeAgo(tweet.created, currentTime.time) }
                return@map it.toMutableList()
            }
            .subscribe({
                tweets = it
                viewState.hideLoading()
                viewState.showTweets()
            }, {
                it.printStackTrace()
                viewState.hideLoading()
                viewState.onErrorLoadingTweets()
            })
    }

    private fun getTimeAgo(time: Long, nowTime: Long): Time {
        val now = (nowTime / SECOND_MILLISECONDS).toInt()
        if (time / SECOND_MILLISECONDS > now || time <= 0)
            return Time((time / SECOND_MILLISECONDS).toInt(), TimePointer.Later)

        val diff = (now - time / SECOND_MILLISECONDS).toInt()
        return when {
            diff < MINUTE_SECONDS -> Time(diff, TimePointer.Seconds)
            diff < 50 * MINUTE_SECONDS -> Time(diff / MINUTE_SECONDS, TimePointer.Minutes)
            diff < 24 * HOUR_SECONDS -> Time(diff / HOUR_SECONDS, TimePointer.Hours)
            diff < 48 * HOUR_SECONDS -> Time(
                (time / SECOND_MILLISECONDS).toInt(),
                TimePointer.Yesterday
            )
            else -> Time((time / SECOND_MILLISECONDS).toInt(), TimePointer.Later)
        }
    }

    override fun detachView(view: TweetsView?) {
        tweetsDisposable?.dispose()
        tweetPendingDisposable?.dispose()
        tweetPushingDisposable?.dispose()
        super.detachView(view)
    }
}

private const val SECOND_MILLISECONDS = 1000
private const val MINUTE_SECONDS = 60
private const val HOUR_SECONDS = 60 * MINUTE_SECONDS