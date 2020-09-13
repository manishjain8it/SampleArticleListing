package com.manish.articlelisting.article

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.manish.articlelisting.article.model.ArticleItem
import com.manish.articlelisting.common.BaseViewModel
import com.manish.articlelisting.common.Constants
import com.manish.articlelisting.util.Resource
import com.manish.articleslistingsample.article.usecase.GetArticleListUseCase
import io.reactivex.schedulers.Schedulers

class ArticleListViewModel @ViewModelInject constructor(
    private val articleListUseCase: GetArticleListUseCase
) : BaseViewModel() {

    private val articleList = MutableLiveData<Resource<List<ArticleItem>>>()
    private var currentPage: Int = Constants.DEFAULT_PAGE
    private val pageLimit: Int = Constants.DEFAULT_PAGE_SIZE
    private val totalPages: Int = Constants.TOTAL_PAGE_SIZE

    companion object {
        private val TAG = "ArticleListViewModel"
    }

    init {
        fetchArticles()
    }

    private fun fetchArticles() {
        if (currentPage == Constants.DEFAULT_PAGE)
            articleList.postValue(Resource.loading(null))

        val disposable = articleListUseCase.getArticleList(currentPage, pageLimit)
            .subscribeOn(Schedulers.io())
            .subscribe({ response ->
                Log.d(TAG, "On Subscribe Called")
                Log.d(TAG, "Response count ${response.size}")
                articleList.postValue(Resource.success(response))

            }, { error ->
                Log.d(TAG, "On Error Called")
                articleList.postValue(Resource.error(error.message.toString(), null))
            })
        addDisposable(disposable)
    }

    fun loadMoreArticles() {
        Log.d(TAG, "loadMoreArticles, currentPage $currentPage")
        if (currentPage < totalPages) {
            currentPage += 1
            fetchArticles()
        } else if (currentPage == totalPages) {
            articleList.postValue(Resource.success(null))
        }
    }

    fun getArticleList() = articleList
}