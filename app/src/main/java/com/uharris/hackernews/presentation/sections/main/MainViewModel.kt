package com.uharris.hackernews.presentation.sections.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.uharris.hackernews.data.base.Failure
import com.uharris.hackernews.data.base.Result
import com.uharris.hackernews.domain.models.News
import com.uharris.hackernews.domain.usecases.actions.FetchNews
import com.uharris.hackernews.domain.usecases.base.UseCase
import com.uharris.hackernews.presentation.state.Resource
import com.uharris.hackernews.presentation.state.ResourceState
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val fetchNews: FetchNews,
    application: Application
): AndroidViewModel(application) {

    val newsLiveData: MutableLiveData<Resource<List<News>>> = MutableLiveData()

    fun fetchNews() {
        newsLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        fetchNews(UseCase.None()){
            when(it){
                is Result.Success -> handleRepositories(it.data.list)
                is Result.Error -> handleError(it.failure)
            }
        }
    }

    private fun handleRepositories(news: List<News>) {

        newsLiveData.postValue(Resource(ResourceState.SUCCESS, news, null))
    }

    private fun handleError(failure: Failure) {
        val message = when (failure) {
            is Failure.NetworkConnection -> "Error with network connection"
            else -> "Error with the server."
        }

        newsLiveData.postValue(Resource(ResourceState.ERROR, null, message))
    }
}