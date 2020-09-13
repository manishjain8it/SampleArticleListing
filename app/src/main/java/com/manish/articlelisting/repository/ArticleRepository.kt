package com.manish.articlelisting.repository

import com.manish.articlelisting.article.model.ArticleItem
import io.reactivex.Single

interface ArticleRepository {
    fun getArticles(page: Int, perPage: Int): Single<List<ArticleItem>>
}