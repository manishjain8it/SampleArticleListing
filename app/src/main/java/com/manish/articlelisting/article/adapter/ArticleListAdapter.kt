package com.manish.articlelisting.article.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manish.articlelisting.R
import com.manish.articlelisting.article.model.ArticleItem

class ArticleListAdapter(private var articles: ArrayList<ArticleItem?>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.listitem_article, parent, false)
                ArticleViewHolder(itemView)
            }
            VIEW_TYPE_LOADING -> {
                val itemView =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.listitem_progress, parent, false)
                LoadingViewHolder(itemView)
            }
            else -> {
                val itemView =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.listitem_progress, parent, false)
                LoadingViewHolder(itemView)
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return if(articles[position] == null)
            VIEW_TYPE_LOADING
        else
            VIEW_TYPE_ITEM
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_ITEM -> {
                val articleViewHolder = holder as ArticleViewHolder
                val article = articles.get(position)
                article?.let {
                    articleViewHolder.bind(it)
                }
            }
            VIEW_TYPE_LOADING -> {

            }
        }
    }

    fun updateData(articleList: ArrayList<ArticleItem>?) {
        articleList?.run { articles.addAll(this) }
        notifyDataSetChanged()
    }

    fun addNullData() {
        articles.add(null)
        notifyItemInserted(articles.size - 1)
    }

    fun removeNull() {
        if(articles.isNotEmpty() && getItemViewType(articles.size - 1) == VIEW_TYPE_LOADING) {
            articles.removeAt(articles.size - 1)
            notifyItemRemoved(articles.size)
        }
    }
}