package com.uharris.hackernews.domain.models.response

import com.google.gson.annotations.SerializedName
import com.uharris.hackernews.domain.models.News

data class NewsResponse(
    @SerializedName("hits") val list: List<News> = mutableListOf(),
    @SerializedName("nbPages") val pages: Int = 0)