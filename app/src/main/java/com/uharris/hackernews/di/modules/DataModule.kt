package com.uharris.hackernews.di.modules

import com.uharris.hackernews.BuildConfig
import com.uharris.hackernews.data.NewsRemote
import com.uharris.hackernews.data.remote.NewsRemoteImpl
import com.uharris.hackernews.data.services.HackerNewsServiceFactory
import com.uharris.hackernews.data.services.NewsServices
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class DataModule {

    @Module
    companion object{
        @Provides
        @JvmStatic
        fun provideNewsService(): NewsServices {
            return HackerNewsServiceFactory.makeService(BuildConfig.DEBUG)
        }
    }

    @Binds
    abstract fun bindNewsRemote(newsRemote: NewsRemoteImpl): NewsRemote
}