package com.manish.articlelisting.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manish.articlelisting.R
import com.manish.articlelisting.article.model.ArticleItem
import com.manish.articlelisting.article.adapter.ArticleListAdapter
import com.manish.articlelisting.util.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_article_list.*

@AndroidEntryPoint
class ArticleListActivity : AppCompatActivity() {

    private lateinit var viewModel: ArticleListViewModel
    private var articleListAdapter: ArticleListAdapter? = null
    var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_list)

        viewModel = ViewModelProvider(this).get(ArticleListViewModel::class.java)
        initialiseRecyclerView()
        initialiseObservers()

        rvArticles.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val linearLayoutManager: LinearLayoutManager? =
                    recyclerView.layoutManager as? LinearLayoutManager
                if (!isLoading) {
                    linearLayoutManager?.run {
                        if (this.findLastCompletelyVisibleItemPosition() == articleListAdapter?.itemCount!! - 1) {
                            articleListAdapter?.addNullData()
                            viewModel.loadMoreArticles()
                            isLoading = true
                        }
                    }
                }
            }
        })
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
            removeNullIfAdded()
            when (it.status) {
                Status.SUCCESS -> {
                    progressbar.visibility = View.GONE
                    it.data?.let { list ->
                        showArticles(list as ArrayList<ArticleItem>)
                    }
                }
                Status.LOADING -> {
                    progressbar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressbar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

                    // only when adapter is empty, then show no articles found
                    if (articleListAdapter?.itemCount == 0) {
                        showArticles(arrayListOf())
                    }
                }
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

    private fun removeNullIfAdded() {
        articleListAdapter?.removeNull()
        isLoading = false
    }
}