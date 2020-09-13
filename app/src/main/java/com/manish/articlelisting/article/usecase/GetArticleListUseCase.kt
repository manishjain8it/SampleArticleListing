package com.manish.articleslistingsample.article.usecase


import com.manish.articlelisting.article.model.ArticleItem
import com.manish.articlelisting.repository.ArticleRepository
import io.reactivex.Single
import javax.inject.Inject

class GetArticleListUseCase @Inject constructor(private val repository: ArticleRepository) {

    fun getArticleList(page : Int, perPage : Int): Single<List<ArticleItem>> {
        return repository.getArticles(page, perPage)
    }
}