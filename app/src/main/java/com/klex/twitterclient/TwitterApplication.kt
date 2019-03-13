package com.klex.twitterclient

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.RegisterMoxyReflectorPackages
import com.klex.twitter.implementations.initTwitter
import com.klex.twitterclient.di.ApplicationModule
import com.klex.twitterclient.di.DaggerAppMainComponent
import com.klex.twitterclient.di.TwitterModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

@RegisterMoxyReflectorPackages("com.klex.presentation", "com.klex.ui")
class TwitterApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun activityInjector(): AndroidInjector<Activity> = androidInjector

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    override fun onCreate() {
        super.onCreate()
        initTwitter(this)

        DaggerAppMainComponent.builder()
            .application(this)
            .applicationModule(ApplicationModule(this))
            .networkModule(TwitterModule())
            .build()
            .inject(this)
    }
}