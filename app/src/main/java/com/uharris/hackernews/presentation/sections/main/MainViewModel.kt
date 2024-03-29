package com.uharris.hackernews.presentation.sections.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.uharris.hackernews.data.base.Failure
import com.uharris.hackernews.data.base.Result
import com.uharris.hackernews.domain.models.News
import com.uharris.hackernews.domain.usecases.actions.DeleteNews
import com.uharris.hackernews.domain.usecases.actions.FetchLocalNews
import com.uharris.hackernews.domain.usecases.actions.FetchNews
import com.uharris.hackernews.domain.usecases.actions.SaveNews
import com.uharris.hackernews.domain.usecases.base.LocalUseCase
import com.uharris.hackernews.domain.usecases.base.UseCase
import com.uharris.hackernews.presentation.base.Completable
import com.uharris.hackernews.presentation.state.Resource
import com.uharris.hackernews.presentation.state.ResourceState
import com.uharris.hackernews.utils.DateUtils
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val fetchNews: FetchNews,
    private val fetchLocalNews: FetchLocalNews,
    private val saveNews: SaveNews,
    private val deleteNews: DeleteNews,
    application: Application
): AndroidViewModel(application) {

    val newsLiveData: MutableLiveData<Resource<List<News>>> = MutableLiveData()

    fun fetchNews() {
        fetchNews(UseCase.None()){
            when(it){
                is Result.Success -> handleRefreshNews(it.data.list)
                is Result.Error -> handleError(it.failure)
            }
        }
    }

    fun fetchLocalNews() {
        newsLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        fetchLocalNews(LocalUseCase.None()) {
            handleLocalNews(it)
        }
    }

    fun deleteNews(id: Long) {
        var currentNews = newsLiveData.value?.data?.toMutableList() ?: mutableListOf()
        var position = getCurrentPosition(id, currentNews)
        var oldNews = currentNews[position]
        newsLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        deleteNews(DeleteNews.Params(id)){
            when(it){
                is Completable.OnComplete -> {
                    currentNews[position] = News(oldNews.id, oldNews.title, oldNews.url, oldNews.storyTitle,
                        oldNews.storyUrl, oldNews.createdAt, oldNews.author, 1)
                    newsLiveData.postValue(Resource(ResourceState.SUCCESS, currentNews, null))
                }
                is Completable.OnError -> newsLiveData.postValue(Resource(ResourceState.ERROR, null, it.throwable.localizedMessage))
            }
        }
    }

    private fun getCurrentPosition(id: Long, currentNews: MutableList<News>): Int {
        var position = 0
        for(news in currentNews){
            if(news.id == id){
                break
            }
            position++
        }

        return position
    }

    private fun handleLocalNews(localNews: List<News>) {
        if(localNews.isNotEmpty()) {
            newsLiveData.postValue(Resource(ResourceState.SUCCESS, localNews, null))
        }else{
            fetchNews(UseCase.None()){
                when(it){
                    is Result.Success -> handleNews(it.data.list)
                    is Result.Error -> handleError(it.failure)
                }
            }
        }
    }

    private fun handleNews(news: List<News>){
        saveNews(SaveNews.Params(news)){
            when(it){
                is Completable.OnComplete -> newsLiveData.postValue(Resource(ResourceState.SUCCESS, news, null))
                is Completable.OnError -> newsLiveData.postValue(Resource(ResourceState.ERROR, null, it.throwable.localizedMessage))
            }
        }
    }

    private fun handleRefreshNews(news: List<News>){
        val oldNews = newsLiveData.value?.data ?: mutableListOf()
        val filterNews = getFilterNews(news, oldNews)

        if(filterNews.isNotEmpty()) {
            saveNews(SaveNews.Params(filterNews)){
                when(it){
                    is Completable.OnComplete -> {
                        val data = listOf(filterNews, oldNews)
                        val combined = data.flatten()

                        newsLiveData.postValue(Resource(ResourceState.SUCCESS, combined, null))
                    }
                    is Completable.OnError ->
                        newsLiveData.postValue(Resource(ResourceState.ERROR, null, it.throwable.localizedMessage))
                }
            }
        } else {
            newsLiveData.postValue(Resource(ResourceState.SUCCESS, oldNews, null))
        }
    }

    private fun getFilterNews(news: List<News>, oldNews: List<News>): List<News>{

        var lastDate = DateUtils.parseDate(oldNews[0].createdAt, "yyyy-MM-dd'T'HH:mm:ss'.000Z'")?.time ?: 0

        return news.filter {
            (DateUtils.parseDate(it.createdAt, "yyyy-MM-dd'T'HH:mm:ss'.000Z'")?.time ?: 0) > lastDate
        }
    }

    private fun handleError(failure: Failure) {
        val message = when (failure) {
            is Failure.NetworkConnection -> "Error with network connection"
            else -> "Error with the server."
        }

        newsLiveData.postValue(Resource(ResourceState.ERROR, null, message))
    }
}