package com.manish.articlelisting.article.datasource

import com.manish.articlelisting.article.model.ArticleItem
import io.reactivex.Single

interface ArticleDataSource {
    fun getArticlesByPage(page: Int, perPage: Int): Single<List<ArticleItem>>

    fun saveAllArticles(articles: List<ArticleItem>): Single<List<ArticleItem>>
}