package com.uharris.hackernews.domain.usecases.actions

import com.uharris.hackernews.cache.Cache
import com.uharris.hackernews.domain.models.News
import com.uharris.hackernews.domain.usecases.base.LocalUseCase
import com.uharris.hackernews.presentation.base.Completable
import java.lang.Exception
import javax.inject.Inject

class SaveNews @Inject constructor(private val cache: Cache): LocalUseCase<Completable, SaveNews.Params>(){
    override suspend fun run(params: Params): Completable = cache.saveNews(params.news)



    data class Params(val news: List<News>)
}