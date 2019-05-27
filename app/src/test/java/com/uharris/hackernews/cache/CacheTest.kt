package com.uharris.hackernews.cache

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.uharris.hackernews.cache.db.Database
import com.uharris.hackernews.domain.models.News
import com.uharris.hackernews.factory.ResponseFactory
import com.uharris.hackernews.utils.Mapper
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RuntimeEnvironment
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class CacheTest {

    private var database = Room.inMemoryDatabaseBuilder(
        RuntimeEnvironment.application,
        Database::class.java).allowMainThreadQueries().build()

    private lateinit var cache: Cache

    @Before
    fun setup(){
        cache = CacheImpl(database)
    }

    @Test
    fun saveNewsSavesData(){
        val news = ResponseFactory.makeNewsList()

        runBlocking { cache.saveNews(news) }

        checkNumRowsInCachedNewsTable(news.size)
    }

    @Test
    fun getNewsReturnsData(){
        val news = ResponseFactory.makeNewsList()
        val array = news.map { Mapper.newsToCached(it) }.toTypedArray()

        runBlocking { database.cachedNewsDao.insertNews(news = *array) }

        val newsFromCache = runBlocking { cache.getNews() }

        assertEquals(news, newsFromCache)
    }

    @Test
    fun deleteNews(){
        val news = ResponseFactory.makeNewsList()
        val array = news.map { Mapper.newsToCached(it) }.toTypedArray()

        runBlocking { database.cachedNewsDao.insertNews(news = *array) }

        runBlocking { cache.deleteNews(news[0].id) }

        checkDeletionOfCachedNews(news[0])
    }

    private fun checkDeletionOfCachedNews(news: News) {
        val cachedNews = runBlocking { database.cachedNewsDao.getNews() }
        assert(news.deleted != cachedNews[0].deleted)
    }

    private fun checkNumRowsInCachedNewsTable(expectedRows: Int) {
        val numberOfRows = runBlocking { database.cachedNewsDao.getNews().size }
        assertEquals(expectedRows, numberOfRows)
    }
}