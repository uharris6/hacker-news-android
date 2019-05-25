package com.uharris.hackernews.cache

import com.uharris.hackernews.domain.models.News

interface Cache {

    suspend fun saveNews(news: List<News>)

    suspend fun getNews(): List<News>

    suspend fun deleteNews(id: Long)
}