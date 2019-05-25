package com.uharris.hackernews.di

import android.app.Application
import com.uharris.hackernews.di.modules.*
import com.uharris.hackernews.presentation.base.HackerNewsApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class,
        UIModule::class,
        CacheModule::class,
        DataModule::class,
        PresentationModule::class,
        ApplicationModule::class])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: HackerNewsApplication)
}