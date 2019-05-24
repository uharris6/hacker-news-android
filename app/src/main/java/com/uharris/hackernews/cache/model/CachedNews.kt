package com.uharris.hackernews.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.uharris.hackernews.cache.db.NewsConstants

@Entity(tableName = NewsConstants.TABLE_NAME)
class CachedNews(
    @PrimaryKey
    @ColumnInfo(name = NewsConstants.COLUMN_ID)
    var id: Int,
    var title: String? = "",
    var storyTitle: String? = "",
    var storyUrl: String? = "",
    var url: String? = "",
    var author: String,
    var createdAt: String,
    var deleted: Int = 0
)