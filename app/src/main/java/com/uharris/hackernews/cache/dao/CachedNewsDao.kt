package com.uharris.hackernews.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uharris.hackernews.cache.db.NewsConstants
import com.uharris.hackernews.cache.model.CachedNews

@Dao
abstract class CachedNewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertNews(vararg news: CachedNews)

    @Query(NewsConstants.QUERY_NEWS)
    @JvmSuppressWildcards
    abstract suspend fun getNews(): List<CachedNews>

    @Query("UPDATE ${NewsConstants.TABLE_NAME} SET deleted = 1 WHERE ${NewsConstants.COLUMN_ID} = :newsId")
    abstract suspend fun deleteNews(newsId: Long)

}