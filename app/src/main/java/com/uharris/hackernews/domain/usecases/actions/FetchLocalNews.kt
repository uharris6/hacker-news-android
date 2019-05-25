package com.uharris.hackernews.domain.usecases.actions

import com.uharris.hackernews.cache.Cache
import com.uharris.hackernews.domain.models.News
import com.uharris.hackernews.domain.usecases.base.LocalUseCase
import javax.inject.Inject

class FetchLocalNews @Inject constructor(private val cache: Cache): LocalUseCase<List<News>, LocalUseCase.None>()  {
    override suspend fun run(params: None): List<News> = cache.getNews()
}