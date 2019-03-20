package com.klex.twitterclient.di

import android.app.Application
import com.klex.interactors.InitInteractorImpl
import com.klex.interactors.LoginInteractorImpl
import com.klex.interactors.SchedulerDataSource
import com.klex.interactors.TweetsInteractorImpl
import com.klex.interactors.repositories.InitRepository
import com.klex.interactors.repositories.LoginRepository
import com.klex.interactors.repositories.TweetsRepository
import com.klex.presentation.interfaces.InitInteractor
import com.klex.presentation.interfaces.LoginInteractor
import com.klex.presentation.interfaces.TweetsInteractor
import com.klex.storage.PendingTweet
import com.klex.twitter.implementations.TwitterInit
import com.klex.twitter.implementations.TwitterLogin
import com.klex.twitter.implementations.TwitterTimeLine
import com.klex.twitterclient.adapters.InitAdapter
import com.klex.twitterclient.adapters.LoginAdapter
import com.klex.twitterclient.adapters.TweetsAdapter
import com.klex.twitterclient.repositories.InitRepoImpl
import com.klex.twitterclient.repositories.LoginRepoImpl
import com.klex.twitterclient.repositories.TweetsRepoImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TwitterModule {

    @Provides
    @Singleton
    fun provideTwitterInitRepository(application: Application): InitRepository =
        InitRepoImpl(TwitterInit(application.applicationContext))

    @Provides
    @Singleton
    fun provideTwitterLoginRepository(application: Application): LoginRepository =
        LoginRepoImpl(TwitterLogin(application.applicationContext))

    @Provides
    @Singleton
    fun provideTweetsRepository(application: Application): TweetsRepository =
        TweetsRepoImpl(TwitterTimeLine(), PendingTweet(application.applicationContext))

    @Provides
    @Singleton
    fun provideInitInteractor(initRepository: InitRepository): InitInteractor =
        InitAdapter(InitInteractorImpl(initRepository))

    @Provides
    @Singleton
    fun provideLoginInteractor(loginRepository: LoginRepository): LoginInteractor =
        LoginAdapter(LoginInteractorImpl(loginRepository))

    @Provides
    @Singleton
    fun provideTweetsInteractor(
        tweetsRepository: TweetsRepository,
        scheduler: SchedulerDataSource
    ): TweetsInteractor =
        TweetsAdapter(TweetsInteractorImpl(tweetsRepository, scheduler))
}