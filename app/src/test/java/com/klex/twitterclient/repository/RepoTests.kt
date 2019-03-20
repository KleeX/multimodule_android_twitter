package com.klex.twitterclient.repository

import android.annotation.SuppressLint
import com.klex.storage.TweetPendingEntity
import com.klex.storage.interfaces.IPendingTweet
import com.klex.twitter.TweetResponse
import com.klex.twitter.interfaces.ITwitterInit
import com.klex.twitter.interfaces.ITwitterLogin
import com.klex.twitter.interfaces.ITwitterTimeLine
import com.klex.twitterclient.checkException
import com.klex.twitterclient.repositories.InitRepoImpl
import com.klex.twitterclient.repositories.LoginRepoImpl
import com.klex.twitterclient.repositories.TweetsRepoImpl
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import java.text.SimpleDateFormat
import java.util.*

class RepoTests {
    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var twitterInit: ITwitterInit
    @Mock
    lateinit var twitterLogin: ITwitterLogin
    @Mock
    lateinit var twitterTimeLine: ITwitterTimeLine
    @Mock
    lateinit var pendingTweet: IPendingTweet

    @InjectMocks
    lateinit var initRepoImpl: InitRepoImpl

    @InjectMocks
    lateinit var loginRepoImpl: LoginRepoImpl

    @InjectMocks
    lateinit var tweetsRepoImpl: TweetsRepoImpl

    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy")

    @Test
    fun testInit() {
        Mockito.doReturn(true).`when`(twitterInit).isAuthenticated
        assertTrue(initRepoImpl.isAuthenticated)

        Mockito.doReturn(false).`when`(twitterInit).isAuthenticated
        assertTrue(!initRepoImpl.isAuthenticated)

        checkException(initRepoImpl::reinitTwitter)
        verify(twitterInit, times(1)).reinitTwitter()
    }

    @Test
    fun testLogin() {
        checkException(loginRepoImpl::authenticate)
        verify(twitterLogin, times(1)).authenticate()

        checkException(loginRepoImpl::logout)
        verify(twitterLogin, times(1)).logout()
    }

    @Test
    fun testTweets() {
        val text = "text"
        val picture = "https://picture"
        val pendingTweetEntity = TweetPendingEntity(text, picture)

        checkException { tweetsRepoImpl.pendingTweet(text, picture) }
        verify(pendingTweet, times(1)).putPendingTweet(text, picture)

        `when`(pendingTweet.observePendingTweet()).thenReturn(Observable.just(pendingTweetEntity))
        tweetsRepoImpl.observePendingTweet()
            .test()
            .assertValue {
                return@assertValue pendingTweetEntity.picturePath == it.picturePath
                        && pendingTweetEntity.text == it.text
            }

        val tweet0 = TweetResponse(
            username = "0",
            nickname = "0",
            userAvatar = "0",
            textContent = "0",
            pictures = listOf("0"),
            created = dateFormat.format(Date())
        )

        val tweet1 = TweetResponse(
            username = "1",
            nickname = "1",
            userAvatar = "1",
            textContent = "1",
            pictures = listOf("0"),
            created = dateFormat.format(Date())
        )

        val tweet2 = TweetResponse(
            username = "2",
            nickname = "2",
            userAvatar = "2",
            textContent = "2",
            pictures = listOf("0"),
            created = dateFormat.format(Date())
        )
        `when`(twitterTimeLine.singleTweets).thenReturn(Single.just(listOf(tweet0, tweet1, tweet2)))
        tweetsRepoImpl.tweets
            .test()
            .assertValue {
                return@assertValue it[0].username == tweet0.username
                        && it[0].nickname == tweet0.nickname
                        && it[0].userAvatar == tweet0.userAvatar
                        && it[0].textContent == tweet0.textContent
                        && it[0].pictureUrl == tweet0.pictures.first()
                        && it[0].created == dateFormat.parse(tweet0.created).time

                        && it[1].username == tweet1.username
                        && it[1].nickname == tweet1.nickname
                        && it[1].userAvatar == tweet1.userAvatar
                        && it[1].textContent == tweet1.textContent
                        && it[1].pictureUrl == tweet1.pictures.first()
                        && it[1].created == dateFormat.parse(tweet1.created).time

                        && it[2].username == tweet2.username
                        && it[2].nickname == tweet2.nickname
                        && it[2].userAvatar == tweet2.userAvatar
                        && it[2].textContent == tweet2.textContent
                        && it[2].pictureUrl == tweet2.pictures.first()
                        && it[2].created == dateFormat.parse(tweet2.created).time
            }

        `when`(twitterTimeLine.pushTweet(text, picture)).thenReturn(Single.just(tweet0))
        tweetsRepoImpl.pushTweet(text, picture)
            .test()
            .assertValue {
                return@assertValue it.username == tweet0.username
                        && it.nickname == tweet0.nickname
                        && it.userAvatar == tweet0.userAvatar
                        && it.textContent == tweet0.textContent
                        && it.pictureUrl == tweet0.pictures.first()
                        && it.created == dateFormat.parse(tweet0.created).time
            }
    }
}