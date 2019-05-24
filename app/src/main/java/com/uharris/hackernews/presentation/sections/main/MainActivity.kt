package com.uharris.hackernews.presentation.sections.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.uharris.hackernews.R
import com.uharris.hackernews.domain.models.News
import com.uharris.hackernews.presentation.base.BaseActivity
import com.uharris.hackernews.presentation.base.ViewModelFactory
import com.uharris.hackernews.presentation.sections.web.WebActivity
import com.uharris.hackernews.presentation.state.Resource
import com.uharris.hackernews.presentation.state.ResourceState
import dagger.android.AndroidInjection

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var mainViewModel: MainViewModel

    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        AndroidInjection.inject(this)

        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        mainViewModel.newsLiveData.observe(this, Observer {
            handleDataState(it)
        })

        setupUI()

        mainViewModel.fetchNews()

        swipeContainer.setOnRefreshListener {
            mainViewModel.fetchNews()
        }
    }

    private fun setupUI() {
        newsRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(mutableListOf()) { news ->
            val url = if(news.url.isNullOrEmpty()) news.storyUrl else news.url
            WebActivity.startActivity(this, url)
        }
        newsRecyclerView.adapter = adapter
    }

    private fun handleDataState(resource: Resource<List<News>>) {
        when (resource.status) {
            ResourceState.SUCCESS -> {
                resource.data?.let {
                    if(swipeContainer.isRefreshing){
                        swipeContainer.isRefreshing = false
                    }
                    adapter.setItems(it)
                }
            }
            ResourceState.LOADING -> {
            }
            ResourceState.ERROR -> {
                showMessage(resource.message ?: "")
            }
        }
    }
}
