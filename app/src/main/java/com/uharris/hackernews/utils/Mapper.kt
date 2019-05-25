package com.uharris.hackernews.utils

import com.uharris.hackernews.cache.model.CachedNews
import com.uharris.hackernews.domain.models.News

object Mapper {
    fun newsFromCached(cachedNews: CachedNews): News {
        return News(
            id = cachedNews.id,
            title = cachedNews.title ?: "",
            storyTitle = cachedNews.storyTitle ?: "",
            storyUrl = cachedNews.storyUrl ?: "",
            author = cachedNews.author,
            createdAt = cachedNews.createdAt,
            deleted = cachedNews.deleted)
    }

    fun newsToCached(news: News): CachedNews {
        return CachedNews(
            id = news.id,
            title =  news.title,
            storyTitle = news.storyTitle,
            storyUrl = news.storyUrl,
            author = news.author,
            createdAt = news.createdAt
        )
    }
}