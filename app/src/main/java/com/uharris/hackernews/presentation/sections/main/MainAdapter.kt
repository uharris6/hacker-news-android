package com.uharris.hackernews.presentation.sections.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uharris.hackernews.R
import com.uharris.hackernews.domain.models.News
import com.uharris.hackernews.utils.DateUtils
import kotlinx.android.synthetic.main.item_news.view.*

class MainAdapter(private var news: MutableList<News>, private val listener: (News) -> Unit):
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false))
    }

    override fun getItemCount() = news.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(news[position], listener)

    fun setItems(newsList: List<News>) {
        news.clear()
        news.addAll(newsList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(item: News, listener: (News) -> Unit) = with(itemView) {
            titleTextView.text = if(item.title.isNullOrEmpty()) item.storyTitle else item.title
            authorTextView.text = item.author
            dateTextView.text = DateUtils.offsetFrom(item.createdAt)

            setOnClickListener { listener(item) }
        }
    }
}