package com.uharris.hackernews.cache

import com.uharris.hackernews.cache.db.Database
import com.uharris.hackernews.domain.models.News
import com.uharris.hackernews.utils.Mapper
import javax.inject.Inject

class CacheImpl @Inject constructor(private val database: Database): Cache {
    override suspend fun deleteNews(id: Long) {
        database.cachedNewsDao.deleteNews(id)
    }

    override suspend fun saveNews(news: List<News>) {
        val array = news.map { Mapper.newsToCached(it) }.toTypedArray()

        database.cachedNewsDao.insertNews(news = *array)
    }

    override suspend fun getNews(): List<News> {
        return database.cachedNewsDao.getNews().map { Mapper.newsFromCached(it) }
    }
}