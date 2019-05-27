package com.uharris.hackernews.cache

import com.uharris.hackernews.cache.db.Database
import com.uharris.hackernews.domain.models.News
import com.uharris.hackernews.presentation.base.Completable
import com.uharris.hackernews.utils.Mapper
import javax.inject.Inject

class CacheImpl @Inject constructor(private val database: Database): Cache {
    override suspend fun deleteNews(id: Long): Completable {
        return try {
            database.cachedNewsDao.deleteNews(id)
            Completable.OnComplete
        }catch (e: Exception){
            Completable.OnError(e)
        }
    }

    override suspend fun saveNews(news: List<News>): Completable {
        val array = news.map { Mapper.newsToCached(it) }.toTypedArray()

        return try {
            database.cachedNewsDao.insertNews(news = *array)
            Completable.OnComplete
        }catch (e: Exception){
            Completable.OnError(e)
        }

    }

    override suspend fun getNews(): List<News> {
        return database.cachedNewsDao.getNews().map { Mapper.newsFromCached(it) }
    }
}