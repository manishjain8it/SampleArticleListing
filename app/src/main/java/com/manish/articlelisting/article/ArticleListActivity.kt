package com.manish.articlelisting.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.manish.articlelisting.R
import com.manish.articlelisting.article.model.ArticleItem
import com.manish.articlelisting.article.adapter.ArticleListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_article_list.*

@AndroidEntryPoint
class ArticleListActivity : AppCompatActivity() {

    private lateinit var viewModel: ArticleListViewModel
    private var articleListAdapter: ArticleListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_list)

        viewModel = ViewModelProvider(this).get(ArticleListViewModel::class.java)
        initialiseRecyclerView()
        initialiseObservers()
    }

    private fun initialiseRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        rvArticles.layoutManager = layoutManager
        articleListAdapter = ArticleListAdapter(arrayListOf())
        rvArticles.addItemDecoration(
            DividerItemDecoration(
                rvArticles.context,
                (rvArticles.layoutManager as LinearLayoutManager).orientation
            )
        )
        rvArticles.adapter = articleListAdapter
    }

    private fun initialiseObservers() {
        viewModel.getArticleList().observe(this, {
            it?.let { list->
                showArticles(list as ArrayList<ArticleItem>)
            }
        })
    }

    private fun showArticles(list: ArrayList<ArticleItem>?) {
        articleListAdapter?.apply {
            updateData(list)

            if (itemCount == 0) {
                tvNoArticles.visibility = View.VISIBLE
                rvArticles.visibility = View.GONE
            } else {
                tvNoArticles.visibility = View.GONE
                rvArticles.visibility = View.VISIBLE
            }
        }

    }
}