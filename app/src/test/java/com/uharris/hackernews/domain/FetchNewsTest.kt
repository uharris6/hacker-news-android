package com.uharris.hackernews.domain

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import com.uharris.hackernews.data.NewsRemote
import com.uharris.hackernews.data.base.Result
import com.uharris.hackernews.domain.usecases.actions.FetchNews
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
class FetchNewsTest {

    @Mock
    private lateinit var newsRemote: NewsRemote

    private lateinit var fetchNews: FetchNews

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        fetchNews = FetchNews(newsRemote)
    }

    @Test
    fun fetchNewsReturnDate(){
        runBlocking {
            val data = Result.Success(ResponseFactory.makeNewsResponse())

            whenever(newsRemote.getNews()).thenReturn(data)

            fetchNews.run(UseCase.None())

            verify(newsRemote).getNews()
            verifyNoMoreInteractions(newsRemote)
        }
    }
}