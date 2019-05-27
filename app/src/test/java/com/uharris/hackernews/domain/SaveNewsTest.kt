package com.uharris.hackernews.domain

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import com.nhaarman.mockito_kotlin.whenever
import com.uharris.hackernews.cache.Cache
import com.uharris.hackernews.domain.usecases.actions.SaveNews
import com.uharris.hackernews.factory.ResponseFactory
import com.uharris.hackernews.presentation.base.Completable
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SaveNewsTest {

    @Mock
    private lateinit var cache: Cache

    private lateinit var saveNews: SaveNews

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        saveNews = SaveNews(cache)
    }

    @Test
    fun saveNews(){
        runBlocking {
            val data = ResponseFactory.makeNewsList()

            whenever(cache.saveNews(data)).thenReturn(Completable.OnComplete)

            saveNews.run(SaveNews.Params(data))

            verify(cache).saveNews(data)
            verifyZeroInteractions(cache)
        }
    }
}