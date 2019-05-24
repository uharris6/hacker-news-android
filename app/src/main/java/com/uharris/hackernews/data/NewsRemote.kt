package com.uharris.hackernews.data

import com.uharris.hackernews.data.base.Result
import com.uharris.hackernews.domain.models.response.NewsResponse

interface NewsRemote {

    suspend fun getNews(): Result<NewsResponse>
}