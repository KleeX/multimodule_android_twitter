package com.klex.twitterclient.di

import com.klex.ui.activities.LoginActivity
import com.klex.ui.activities.MainActivity
import com.klex.ui.activities.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by klex.
 */
@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    internal abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    internal abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    internal abstract fun bindMainActivity(): MainActivity

}