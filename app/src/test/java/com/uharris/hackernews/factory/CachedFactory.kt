package com.uharris.hackernews.factory

import com.uharris.hackernews.cache.model.CachedNews

object CachedFactory {

    fun makeCachedNews(): CachedNews{
        return CachedNews(DataFactory.randomLong(), DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(), 0)
    }

    fun makeCachedNewsList(): List<CachedNews> {
        val list = mutableListOf<CachedNews>()
        list.add(makeCachedNews())
        return list
    }
}