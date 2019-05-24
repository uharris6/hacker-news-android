package com.uharris.hackernews.data.services

import com.uharris.hackernews.domain.models.response.NewsResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsServices {

    @GET("search_by_date?query=android")
    fun getNews(): Deferred<Response<NewsResponse>>
}