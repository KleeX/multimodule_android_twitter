package com.klex.twitterclient.di

import android.app.Application
import com.klex.domain.SchedulerDataSource
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
    fun provideMainThreadScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Singleton
    fun provideIOThreadScheduler(): Scheduler = Schedulers.io()

    @Provides
    @Singleton
    fun provideSchedulerDataSource(): SchedulerDataSource = object : SchedulerDataSource {
        override fun main() = AndroidSchedulers.mainThread()
        override fun io() = Schedulers.io()
    }
}