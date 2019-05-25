package com.uharris.hackernews.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uharris.hackernews.cache.dao.CachedNewsDao
import com.uharris.hackernews.cache.model.CachedNews

@Database(entities = [CachedNews::class], version = 1)
abstract class Database : RoomDatabase() {

    abstract val cachedNewsDao: CachedNewsDao

    companion object {

        private var INSTANCE: com.uharris.hackernews.cache.db.Database? = null
        private val lock = Any()

        fun getInstance(context: Context): com.uharris.hackernews.cache.db.Database {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                            com.uharris.hackernews.cache.db.Database::class.java, "hacker_news_database.db")
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                    return INSTANCE as com.uharris.hackernews.cache.db.Database
                }
            }
            return INSTANCE as com.uharris.hackernews.cache.db.Database
        }
    }
}
