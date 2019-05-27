package com.uharris.hackernews.cache.mapper

import com.uharris.hackernews.cache.model.CachedNews
import com.uharris.hackernews.domain.models.News
import com.uharris.hackernews.factory.CachedFactory
import com.uharris.hackernews.factory.ResponseFactory
import com.uharris.hackernews.utils.Mapper
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class MapperTest {

    @Test
    fun newsToCached(){
        val news = ResponseFactory.makeNews()
        val cachedNews = Mapper.newsToCached(news)

        assertNewsDataEquality(news, cachedNews)
    }

    @Test
    fun newsFromCached(){
        val cachedNews = CachedFactory.makeCachedNews()
        val news = Mapper.newsFromCached(cachedNews)

        assertNewsDataEquality(news, cachedNews)
    }

    private fun assertNewsDataEquality(news: News,
                                           cachedNews: CachedNews) {

        assertEquals(news.id, cachedNews.id)
        assertEquals(news.title, cachedNews.title)
        assertEquals(news.url, cachedNews.url)
        assertEquals(news.storyTitle, cachedNews.storyTitle)
        assertEquals(news.storyUrl, cachedNews.storyUrl)
        assertEquals(news.author, cachedNews.author)
        assertEquals(news.createdAt, cachedNews.createdAt)
        assertEquals(news.deleted, cachedNews.deleted)
    }

}