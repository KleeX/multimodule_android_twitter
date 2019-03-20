package com.klex.twitterclient.presentation

import com.arellomobile.mvp.viewstate.MvpViewState
import com.klex.presentation.TimePointer
import com.klex.presentation.Tweet
import com.klex.presentation.TweetPending
import com.klex.presentation.interfaces.TweetsInteractor
import com.klex.presentation.tweets.TweetsPresenter
import com.klex.presentation.tweets.TweetsView
import com.klex.twitterclient.checkException
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import java.util.*

class TweetsTests {
    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var tweetsInteractor: TweetsInteractor

    @Mock
    lateinit var tweetsViewState: TweetsViewState

    @InjectMocks
    lateinit var tweetsPresenter: TweetsPresenter

    @Before
    fun setUp() {
        tweetsPresenter.setViewState(tweetsViewState)
    }

    @Test
    fun tweetsTests() {
        val tweet = Tweet(
            username = "0",
            nickname = "0",
            userAvatar = "0",
            textContent = "0",
            pictureUrl = "0",
            created = 0L
        )
        var pendingTweet = TweetPending("", "")
        Mockito.`when`(tweetsInteractor.observePendingTweet())
            .thenReturn(Observable.just(pendingTweet))
        checkException(tweetsPresenter::observePendingTweet)
        verify(tweetsViewState, times(0)).showPendingTweet()

        pendingTweet = TweetPending("text", "")
        Mockito.`when`(tweetsInteractor.observePendingTweet())
            .thenReturn(Observable.just(pendingTweet))
        Mockito.`when`(tweetsInteractor.pushTweet(pendingTweet.text, pendingTweet.picturePath))
            .thenReturn(Single.just(tweet))
        checkException(tweetsPresenter::observePendingTweet)
        verify(tweetsViewState, times(1)).showPendingTweet()
        verify(tweetsViewState, times(1)).notifyTweetAdded()
        verify(tweetsViewState, times(1)).hidePendingTweet()

        pendingTweet = TweetPending("", "picture")
        Mockito.`when`(tweetsInteractor.observePendingTweet())
            .thenReturn(Observable.just(pendingTweet))
        Mockito.`when`(tweetsInteractor.pushTweet(pendingTweet.text, pendingTweet.picturePath))
            .thenReturn(Single.just(tweet))
        checkException(tweetsPresenter::observePendingTweet)
        verify(tweetsViewState, times(2)).showPendingTweet()
        verify(tweetsViewState, times(2)).notifyTweetAdded()
        verify(tweetsViewState, times(2)).hidePendingTweet()

        pendingTweet = TweetPending("text", "picture")
        Mockito.`when`(tweetsInteractor.observePendingTweet())
            .thenReturn(Observable.just(pendingTweet))
        Mockito.`when`(tweetsInteractor.pushTweet(pendingTweet.text, pendingTweet.picturePath))
            .thenReturn(Single.just(tweet))
        checkException(tweetsPresenter::observePendingTweet)
        verify(tweetsViewState, times(3)).showPendingTweet()
        verify(tweetsViewState, times(3)).notifyTweetAdded()
        verify(tweetsViewState, times(3)).hidePendingTweet()

        Mockito.`when`(tweetsInteractor.observePendingTweet())
            .thenReturn(Observable.error(RuntimeException()))
        checkException(tweetsPresenter::observePendingTweet)
        verify(tweetsViewState, times(3)).showPendingTweet()
        verify(tweetsViewState, times(3)).notifyTweetAdded()
        verify(tweetsViewState, times(4)).hidePendingTweet()

        Mockito.`when`(tweetsInteractor.observePendingTweet())
            .thenReturn(Observable.just(pendingTweet))
        Mockito.`when`(tweetsInteractor.pushTweet(pendingTweet.text, pendingTweet.picturePath))
            .thenReturn(Single.error(RuntimeException()))
        checkException(tweetsPresenter::observePendingTweet)
        verify(tweetsViewState, times(4)).showPendingTweet()
        verify(tweetsViewState, times(3)).notifyTweetAdded()
        verify(tweetsViewState, times(5)).hidePendingTweet()

        tweetsPresenter.composeTweet()
        verify(tweetsViewState, times(1)).showComposeTweet()
    }

    @Test
    fun testObserveTweets() {
        val tweet0 = Tweet(
            username = "today",
            nickname = "0",
            userAvatar = "0",
            textContent = "0",
            pictureUrl = "0",
            created = Date().time
        )

        val tweet1 = Tweet(
            username = "yesterday",
            nickname = "1",
            userAvatar = "1",
            textContent = "1",
            pictureUrl = "1",
            created = Date().time - 24 * 60 * 61 * 1000
        )

        val tweet2 = Tweet(
            username = "later",
            nickname = "2",
            userAvatar = "2",
            textContent = "2",
            pictureUrl = "2",
            created = Date().time - 48 * 60 * 61 * 1000
        )

        Mockito.`when`(tweetsInteractor.tweets).then {
            Single.just(listOf(tweet1, tweet2, tweet0))
        }
        tweetsPresenter.tweets.clear()
        checkException(tweetsPresenter::checkTweets)
        verify(tweetsViewState, times(1)).showLoading()
        verify(tweetsViewState, times(1)).hideLoading()
        verify(tweetsViewState, times(1)).showTweets()
        assertEquals(tweetsPresenter.tweets[0].time.timePointer, TimePointer.Yesterday)
        assertEquals(tweetsPresenter.tweets[1].time.timePointer, TimePointer.Later)
        assertEquals(tweetsPresenter.tweets[2].time.timePointer, TimePointer.Seconds)
        checkException(tweetsPresenter::checkTweets)
        verify(tweetsViewState, times(1)).showLoading()
    }

    open class TweetsViewState : MvpViewState<TweetsView>(), TweetsView {
        override fun showTweets() = Unit
        override fun onErrorLoadingTweets() = Unit
        override fun showLoading() = Unit
        override fun hideLoading() = Unit
        override fun showPendingTweet() = Unit
        override fun notifyTweetAdded() = Unit
        override fun hidePendingTweet() = Unit
        override fun showComposeTweet() = Unit
    }
}