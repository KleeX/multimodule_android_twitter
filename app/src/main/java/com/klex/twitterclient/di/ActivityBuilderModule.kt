package com.klex.twitterclient.di

import com.klex.ui.SplashActivity
import com.klex.ui.login.LoginActivity
import com.klex.ui.main.MainActivity
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