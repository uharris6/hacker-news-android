package com.uharris.hackernews.domain

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import com.nhaarman.mockito_kotlin.whenever
import com.uharris.hackernews.cache.Cache
import com.uharris.hackernews.domain.usecases.actions.DeleteNews
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
class DeleteNewsTest {

    @Mock
    private lateinit var cache: Cache

    private lateinit var deleteNews: DeleteNews

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        deleteNews = DeleteNews(cache)
    }

    @Test
    fun deleteNews(){
        runBlocking {
            val data = ResponseFactory.makeNews()

            whenever(cache.deleteNews(data.id)).thenReturn(Completable.OnComplete)

            deleteNews.run(DeleteNews.Params(data.id))

            verify(cache).deleteNews(data.id)
            verifyZeroInteractions(cache)
        }
    }
}