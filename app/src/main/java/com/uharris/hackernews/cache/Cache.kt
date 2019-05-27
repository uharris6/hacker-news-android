package com.uharris.hackernews.cache

import com.uharris.hackernews.domain.models.News
import com.uharris.hackernews.presentation.base.Completable

interface Cache {

    suspend fun saveNews(news: List<News>): Completable

    suspend fun getNews(): List<News>

    suspend fun deleteNews(id: Long): Completable
}