package com.uharris.hackernews.domain.models

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("objectID") val id: Long = 0,
    val title: String = "",
    val url: String = "",
    @SerializedName("story_title") val storyTitle: String = "",
    @SerializedName("story_url") val storyUrl: String = "",
    @SerializedName("created_at") val createdAt: String = "",
    val author: String = "")