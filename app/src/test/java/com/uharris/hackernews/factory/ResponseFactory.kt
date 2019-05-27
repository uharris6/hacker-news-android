package com.uharris.hackernews.factory

import com.uharris.hackernews.domain.models.News
import com.uharris.hackernews.domain.models.response.NewsResponse

object ResponseFactory {

    fun makeNewsResponse(): NewsResponse{
        return NewsResponse(makeNewsList(), DataFactory.randomInt())
    }

    fun makeNewsList(): List<News>{
        val list = mutableListOf<News>()
        list.add(makeNews())
        return list
    }

    fun makeNews(): News{
        return News(DataFactory.randomLong(), DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(), 0)
    }
}