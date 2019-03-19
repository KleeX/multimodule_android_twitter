package com.klex.domain

import com.klex.domain.repositories.TweetsRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import java.util.*

open class TweetsTests {

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var tweetsRepository: TweetsRepository
    @Mock
    lateinit var schedulerDataSource: SchedulerDataSource

    @InjectMocks
    lateinit var tweetsInteractor: TweetsInteractorImpl


    @Before
    fun initAll() {
        Mockito.`when`(schedulerDataSource.main()).thenReturn(Schedulers.trampoline())
        Mockito.`when`(schedulerDataSource.io()).thenReturn(Schedulers.trampoline())
    }

    @Test
    fun observeTweetsTest() {

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

        Mockito.`when`(tweetsRepository.tweets).then {
            Single.just(listOf(tweet1, tweet2, tweet0))
        }
        tweetsInteractor.tweets
            .test()
            .assertValue {
                return@assertValue it.size == 3
                        && it[2] == tweet0
                        && it[1] == tweet1
                        && it[0] == tweet2
            }
    }

    @Test
    fun createTweetTest() {
        val picPath = "path_to_picture"
        val textContent = "content"

        val createdTweet = Tweet(
            username = "username",
            nickname = "nickname",
            userAvatar = "https://avatar",
            textContent = textContent,
            pictureUrl = "https://picUrl",
            created = Date().time
        )

        Mockito.`when`(tweetsRepository.pushTweet(textContent, picPath))
            .thenReturn(Single.just(createdTweet))

        tweetsInteractor.pushTweet(textContent, picPath)
            .test()
            .assertValue(createdTweet)

        val tweetPending = TweetPending(textContent, picPath)

        Mockito.`when`(tweetsRepository.observePendingTweet())
            .thenReturn(Observable.just(tweetPending))

        tweetsInteractor.observePendingTweet()
            .test()
            .assertValue(tweetPending)

        checkException { tweetsInteractor.pendingTweet(textContent, picPath) }
    }
}