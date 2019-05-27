package com.uharris.hackernews.cache.dao

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.uharris.hackernews.cache.db.Database
import com.uharris.hackernews.factory.CachedFactory
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RuntimeEnvironment

@RunWith(AndroidJUnit4::class)
class CachedNewsDaoTest {

    private lateinit var newsDatabase: Database

    @Before
    fun initDb(){
        newsDatabase = Room.inMemoryDatabaseBuilder(RuntimeEnvironment.application.baseContext, Database::class.java).build()
    }

    @After
    fun closeDb(){
        newsDatabase.close()
    }

    @Test
    fun insertNewsSaveData(){
        val cachedNews = CachedFactory.makeCachedNewsList()
        val array = cachedNews.toTypedArray()

        runBlocking { newsDatabase.cachedNewsDao.insertNews(news = *array) }

        val news = runBlocking { newsDatabase.cachedNewsDao.getNews() }

        assert(news.isNotEmpty())
    }

    @Test
    fun getNewsRetrievesData(){
        val cachedNews = CachedFactory.makeCachedNewsList()
        val array = cachedNews.toTypedArray()

        runBlocking { newsDatabase.cachedNewsDao.insertNews(news = *array) }

        val news = runBlocking { newsDatabase.cachedNewsDao.getNews() }

        assert(news[0].id == cachedNews[0].id)
    }

    @Test
    fun deleteNewsData(){
        val cachedNews = CachedFactory.makeCachedNewsList()
        val array = cachedNews.toTypedArray()

        runBlocking { newsDatabase.cachedNewsDao.insertNews(news = *array) }

        runBlocking { newsDatabase.cachedNewsDao.deleteNews(cachedNews[0].id) }

        val news = runBlocking { newsDatabase.cachedNewsDao.getNews() }

        assert(news[0].deleted == 1)
    }
}