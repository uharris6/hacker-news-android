package com.uharris.hackernews.data

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import com.nhaarman.mockito_kotlin.whenever
import com.uharris.hackernews.data.base.Failure
import com.uharris.hackernews.data.base.Result
import com.uharris.hackernews.data.remote.NewsRemoteImpl
import com.uharris.hackernews.data.services.NewsServices
import com.uharris.hackernews.domain.models.response.NewsResponse
import com.uharris.hackernews.factory.ResponseFactory
import com.uharris.hackernews.presentation.base.NetworkHandler
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class NewsRemoteTest {

    @Mock
    private lateinit var services: NewsServices
    @Mock
    private lateinit var networkHandler: NetworkHandler

    private lateinit var newsRemote: NewsRemote

    @Before
    fun setup(){
         MockitoAnnotations.initMocks(this)

        newsRemote = NewsRemoteImpl(networkHandler, services)
    }

    @Test
    fun getNewsNetworkFailure(){
        given { networkHandler.isConnected }.willReturn(false)

        runBlocking { newsRemote.getNews() }

        verifyZeroInteractions(services)
    }

    @Test
    fun getNewsNetworkNull(){
        given { networkHandler.isConnected }.willReturn(null)

        runBlocking { newsRemote.getNews() }

        verifyZeroInteractions(services)
    }

    @Test
    fun getNewsServerError(){
        val deferred = CompletableDeferred<Response<NewsResponse>>()
        deferred.complete(Response.error(400, ResponseBody.create(MultipartBody.FORM, "Error")))

        whenever(services.getNews()).thenReturn(deferred)

        given { networkHandler.isConnected }.willReturn(true)

        val newsResponse = runBlocking { newsRemote.getNews() }

        assertEquals(Result.Error(Failure.ServerError), newsResponse)
    }

    @Test
    fun getNewsSuccess(){
        val data = ResponseFactory.makeNewsResponse()
        val deferred = CompletableDeferred<Response<NewsResponse>>()
        deferred.complete(Response.success(data))

        whenever(services.getNews()).thenReturn(deferred)

        given { networkHandler.isConnected }.willReturn(true)

        val newsResponse = runBlocking { newsRemote.getNews() }

        assertEquals(Result.Success(data), newsResponse)
    }
}