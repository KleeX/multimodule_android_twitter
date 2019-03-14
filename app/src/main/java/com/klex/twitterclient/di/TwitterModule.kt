package com.klex.twitterclient.di

import android.app.Application
import com.klex.domain.InitInteractorImpl
import com.klex.domain.LoginInteractorImpl
import com.klex.domain.repositories.TwitterInitRepository
import com.klex.domain.repositories.TwitterLoginRepository
import com.klex.presentation.interfaces.InitInteractor
import com.klex.presentation.interfaces.LoginInteractor
import com.klex.twitter.implementations.TwitterInit
import com.klex.twitter.implementations.TwitterLogin
import com.klex.twitterclient.delegates.InitDelegate
import com.klex.twitterclient.delegates.LoginDelegate
import com.klex.twitterclient.repositories.TwitterInitRepoImpl
import com.klex.twitterclient.repositories.TwitterLoginRepoImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TwitterModule {

    @Provides
    @Singleton
    fun provideTwitterInitRepository(application: Application): TwitterInitRepository =
        TwitterInitRepoImpl(TwitterInit(application.applicationContext))

    @Provides
    @Singleton
    fun provideTwitterLoginRepository(application: Application): TwitterLoginRepository =
        TwitterLoginRepoImpl(TwitterLogin(application.applicationContext))

    @Provides
    @Singleton
    fun provideInitInteractor(twitterInitRepository: TwitterInitRepository): InitInteractor =
        InitDelegate(InitInteractorImpl(twitterInitRepository))

    @Provides
    @Singleton
    fun provideLoginInteractor(twitterLoginRepository: TwitterLoginRepository): LoginInteractor =
        LoginDelegate(LoginInteractorImpl(twitterLoginRepository))
}