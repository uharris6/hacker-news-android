package com.uharris.hackernews.di.modules

import android.app.Application
import com.uharris.hackernews.cache.Cache
import com.uharris.hackernews.cache.CacheImpl
import com.uharris.hackernews.cache.db.Database
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class CacheModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun providesDataBase(application: Application): Database {
            return Database.getInstance(application)
        }
    }

    @Binds
    abstract fun bindCache(cache: CacheImpl): Cache
}