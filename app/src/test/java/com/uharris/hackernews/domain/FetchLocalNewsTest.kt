package com.uharris.hackernews.domain

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import com.nhaarman.mockito_kotlin.whenever
import com.uharris.hackernews.cache.Cache
import com.uharris.hackernews.domain.usecases.actions.FetchLocalNews
import com.uharris.hackernews.domain.usecases.base.LocalUseCase
import com.uharris.hackernews.domain.usecases.base.UseCase
import com.uharris.hackernews.factory.ResponseFactory
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FetchLocalNewsTest {

    @Mock
    private lateinit var cache: Cache

    private lateinit var fetchLocalNews: FetchLocalNews

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        fetchLocalNews = FetchLocalNews(cache)
    }

    @Test
    fun fetchNewsLocalReturnData(){
        runBlocking {
            val data = ResponseFactory.makeNewsList()

            whenever(cache.getNews()).thenReturn(data)

            fetchLocalNews.run(LocalUseCase.None())

            verify(cache).getNews()
            verifyZeroInteractions(cache)
        }
    }

}