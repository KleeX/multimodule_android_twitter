package com.klex.twitterclient.di

import android.app.Application
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * Created by klex.
 */

@Module
class ApplicationModule(private var application: Application) {
    @Provides
    @Singleton
    fun provideApplication() = application

    @Provides
    @Singleton
    @MainThread
    fun provideMainThreadScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Singleton
    @IOThread
    fun provideIOThreadScheduler(): Scheduler = Schedulers.io()
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MainThread

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class IOThread