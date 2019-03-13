package com.klex.twitterclient.di

import com.klex.twitterclient.TwitterApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class, AndroidSupportInjectionModule::class,
        ActivityBuilderModule::class, TwitterModule::class]
)
interface AppMainComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: TwitterApplication): Builder

        fun applicationModule(applicationModule: ApplicationModule): Builder
        fun networkModule(networkModule: TwitterModule): Builder
        fun build(): AppMainComponent
    }

    fun inject(app: TwitterApplication)
}