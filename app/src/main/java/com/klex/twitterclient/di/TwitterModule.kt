package com.klex.twitterclient.di

import android.app.Application
import com.klex.domain.InitInteractorImpl
import com.klex.domain.LoginInteractorImpl
import com.klex.domain.TweetsInteractorImpl
import com.klex.domain.repositories.InitRepository
import com.klex.domain.repositories.LoginRepository
import com.klex.domain.repositories.TweetsRepository
import com.klex.presentation.interfaces.InitInteractor
import com.klex.presentation.interfaces.LoginInteractor
import com.klex.presentation.interfaces.TweetsInteractor
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
import io.reactivex.Scheduler
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
        TweetsRepoImpl(TwitterTimeLine(application.applicationContext))

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
        @MainThread mainScheduler: Scheduler,
        @IOThread ioScheduler: Scheduler
    ): TweetsInteractor =
        TweetsAdapter(TweetsInteractorImpl(tweetsRepository, mainScheduler, ioScheduler))
}