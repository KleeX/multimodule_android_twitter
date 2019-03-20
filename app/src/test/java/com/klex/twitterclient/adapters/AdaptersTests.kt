package com.klex.twitterclient.adapters

import com.klex.interactors.Tweet
import com.klex.interactors.TweetPending
import com.klex.interactors.interfaces.IInitInteractor
import com.klex.interactors.interfaces.ILoginInteractor
import com.klex.interactors.interfaces.ITweetsInteractor
import com.klex.twitterclient.checkException
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class AdaptersTests {
    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var initInteractor: IInitInteractor

    @InjectMocks
    lateinit var initAdapter: InitAdapter

    @Mock
    lateinit var loginInteractor: ILoginInteractor

    @InjectMocks
    lateinit var loginAdapter: LoginAdapter

    @Mock
    lateinit var tweetsInteractor: ITweetsInteractor

    @InjectMocks
    lateinit var tweetsAdapter: TweetsAdapter

    @Test
    fun initAdapterTests() {
        doReturn(true).`when`(initInteractor).isAuthenticated
        assertTrue(initAdapter.isAuthenticated)
        doReturn(false).`when`(initInteractor).isAuthenticated
        assertTrue(!initAdapter.isAuthenticated)
    }

    @Test
    fun loginAdapterTests() {
        checkException(loginAdapter::authenticate)
        verify(loginInteractor, times(1)).authenticate()
        checkException(loginAdapter::logout)
        verify(loginInteractor, times(1)).logout()
    }

    @Test
    fun tweetsAdapterTests() {
        val text = "text"
        val picture = "https://picture"
        val pendingTweet = TweetPending(text, picture)

        checkException { tweetsAdapter.pendingTweet(text, picture) }
        verify(tweetsInteractor, times(1)).pendingTweet(text, picture)

        `when`(tweetsInteractor.observePendingTweet()).thenReturn(Observable.just(pendingTweet))
        tweetsAdapter.observePendingTweet()
            .test()
            .assertValue {
                return@assertValue pendingTweet.picturePath == it.picturePath
                        && pendingTweet.text == it.text
            }

        val tweet0 = Tweet(
            username = "0",
            nickname = "0",
            userAvatar = "0",
            textContent = "0",
            pictureUrl = "0",
            created = 0L
        )

        val tweet1 = Tweet(
            username = "1",
            nickname = "1",
            userAvatar = "1",
            textContent = "1",
            pictureUrl = "1",
            created = 1L
        )

        val tweet2 = Tweet(
            username = "2",
            nickname = "2",
            userAvatar = "2",
            textContent = "2",
            pictureUrl = "2",
            created = 2L
        )
        `when`(tweetsInteractor.tweets).thenReturn(Single.just(listOf(tweet0, tweet1, tweet2)))
        tweetsAdapter.tweets
            .test()
            .assertValue {
                return@assertValue it[0].username == tweet0.username
                        && it[0].nickname == tweet0.nickname
                        && it[0].userAvatar == tweet0.userAvatar
                        && it[0].textContent == tweet0.textContent
                        && it[0].pictureUrl == tweet0.pictureUrl
                        && it[0].created == tweet0.created

                        && it[1].username == tweet1.username
                        && it[1].nickname == tweet1.nickname
                        && it[1].userAvatar == tweet1.userAvatar
                        && it[1].textContent == tweet1.textContent
                        && it[1].pictureUrl == tweet1.pictureUrl
                        && it[1].created == tweet1.created

                        && it[2].username == tweet2.username
                        && it[2].nickname == tweet2.nickname
                        && it[2].userAvatar == tweet2.userAvatar
                        && it[2].textContent == tweet2.textContent
                        && it[2].pictureUrl == tweet2.pictureUrl
                        && it[2].created == tweet2.created
            }

        `when`(tweetsInteractor.pushTweet(text, picture)).thenReturn(Single.just(tweet0))
        tweetsAdapter.pushTweet(text, picture)
            .test()
            .assertValue {
                println(it)
                return@assertValue it.username == tweet0.username
                        && it.nickname == tweet0.nickname
                        && it.userAvatar == tweet0.userAvatar
                        && it.textContent == tweet0.textContent
                        && it.pictureUrl == tweet0.pictureUrl
                        && it.created == tweet0.created
            }
    }
}