package com.uharris.hackernews.domain.usecases.actions

import com.uharris.hackernews.data.NewsRemote
import com.uharris.hackernews.data.base.Result
import com.uharris.hackernews.domain.models.response.NewsResponse
import com.uharris.hackernews.domain.usecases.base.UseCase
import javax.inject.Inject

class FetchNews @Inject constructor(private val newsRemote: NewsRemote): UseCase<NewsResponse, UseCase.None>() {
    override suspend fun run(params: None): Result<NewsResponse> = newsRemote.getNews()
}