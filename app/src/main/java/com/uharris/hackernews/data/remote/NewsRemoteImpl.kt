package com.uharris.hackernews.data.remote

import com.uharris.hackernews.data.NewsRemote
import com.uharris.hackernews.data.base.BaseRepository
import com.uharris.hackernews.data.base.Failure
import com.uharris.hackernews.data.base.Result
import com.uharris.hackernews.data.services.NewsServices
import com.uharris.hackernews.domain.models.response.NewsResponse
import com.uharris.hackernews.presentation.base.NetworkHandler
import javax.inject.Inject

class NewsRemoteImpl @Inject constructor(private val networkHandler: NetworkHandler,
                                         private val newsServices: NewsServices): BaseRepository(), NewsRemote {
    override suspend fun getNews(): Result<NewsResponse> {
        return when(networkHandler.isConnected) {
            true -> safeApiCall(newsServices.getNews(), NewsResponse())
            false, null -> Result.Error(Failure.NetworkConnection)
        }
    }
}