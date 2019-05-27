package com.uharris.hackernews.presentation.sections.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.uharris.hackernews.R
import com.uharris.hackernews.domain.models.News
import com.uharris.hackernews.presentation.base.BaseActivity
import com.uharris.hackernews.presentation.base.SwipeToDeleteCallback
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

        mainViewModel.fetchLocalNews()

        swipeContainer.setOnRefreshListener {
            mainViewModel.fetchNews()
        }
    }

    private fun setupUI() {
        newsRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(mutableListOf()) { news ->
           var url = when{
               news.url.isNotEmpty() -> news.url
               news.storyUrl.isNotEmpty() -> news.storyUrl
               else -> ""
            }

            if(url.isEmpty()){
                showMessage("This article does not have url to show.")
            } else{
                WebActivity.startActivity(this, url)
            }
        }
        newsRecyclerView.adapter = adapter
        val icon = ContextCompat.getDrawable(this,
            R.drawable.baseline_delete_24)
        icon?.setTint(ContextCompat.getColor(this, android.R.color.white))
        val background = ColorDrawable(Color.RED)
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(icon, background){
            mainViewModel.deleteNews(adapter.getItem(it).id)
            adapter.removeItemAt(it, true)
        })
        itemTouchHelper.attachToRecyclerView(newsRecyclerView)
    }

    private fun handleDataState(resource: Resource<List<News>>) {
        if(swipeContainer.isRefreshing){
            swipeContainer.isRefreshing = false
        }
        when (resource.status) {
            ResourceState.SUCCESS -> {
                resource.data?.let {
                    if(it.isNotEmpty()){
                        val filterNews = it.filter { news -> news.deleted == 0 }
                        adapter.setItems(filterNews)
                    }
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
