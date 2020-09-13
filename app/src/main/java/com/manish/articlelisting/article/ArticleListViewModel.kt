package com.manish.articlelisting.article

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.manish.articlelisting.article.model.ArticleItem
import com.manish.articlelisting.common.BaseViewModel
import com.manish.articlelisting.common.Constants
import com.manish.articleslistingsample.article.usecase.GetArticleListUseCase
import io.reactivex.schedulers.Schedulers

class ArticleListViewModel @ViewModelInject constructor(
    private val articleListUseCase: GetArticleListUseCase) : BaseViewModel() {

    private val articleList = MutableLiveData<List<ArticleItem>>()
    private var currentPage: Int = Constants.DEFAULT_PAGE
    private val pageLimit: Int = Constants.DEFAULT_PAGE_SIZE

    companion object {
        private val TAG = "ArticleListViewModel"
    }

    init {
        fetchArticles()
    }

    private fun fetchArticles() {
        val disposable = articleListUseCase.getArticleList(currentPage, pageLimit)
            .subscribeOn(Schedulers.io())
            .subscribe({ response ->
                Log.d(TAG, "On Subscribe Called")
                Log.d(TAG, "Response count ${response.size}"  )
                articleList.postValue(response)

            }, { error ->
                Log.d(TAG, "On Error Called")
            })
        addDisposable(disposable)
    }
}