package com.klex.twitterclient.di

import android.app.Application
import com.klex.domain.InitInteractorImpl
import com.klex.domain.LoginInteractorImpl
import com.klex.domain.datasources.TwitterInitDataSource
import com.klex.domain.datasources.TwitterLoginDataSource
import com.klex.presentation.interfaces.InitInteractor
import com.klex.presentation.interfaces.LoginInteractor
import com.klex.twitter.implementations.TwitterInit
import com.klex.twitter.implementations.TwitterLogin
import com.klex.twitterclient.adapters.InitAdapter
import com.klex.twitterclient.adapters.LoginAdapter
import com.klex.twitterclient.sources.TwitterInitSource
import com.klex.twitterclient.sources.TwitterLoginSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TwitterModule {

    @Provides
    @Singleton
    fun provideTwitterInitRepository(application: Application): TwitterInitDataSource =
        TwitterInitSource(TwitterInit(application.applicationContext))

    @Provides
    @Singleton
    fun provideTwitterLoginRepository(application: Application): TwitterLoginDataSource =
        TwitterLoginSource(TwitterLogin(application.applicationContext))

    @Provides
    @Singleton
    fun provideInitInteractor(twitterInitDataSource: TwitterInitDataSource): InitInteractor =
        InitAdapter(InitInteractorImpl(twitterInitDataSource))

    @Provides
    @Singleton
    fun provideLoginInteractor(twitterLoginDataSource: TwitterLoginDataSource): LoginInteractor =
        LoginAdapter(LoginInteractorImpl(twitterLoginDataSource))
}