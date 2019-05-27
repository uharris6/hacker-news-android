package com.uharris.hackernews.domain.usecases.actions

import com.uharris.hackernews.cache.Cache
import com.uharris.hackernews.domain.usecases.base.LocalUseCase
import com.uharris.hackernews.presentation.base.Completable
import java.lang.Exception
import javax.inject.Inject

class DeleteNews @Inject constructor(private val cache: Cache): LocalUseCase<Completable, DeleteNews.Params>(){
    override suspend fun run(params: Params): Completable = cache.deleteNews(params.id)

    data class Params(val id: Long)
}