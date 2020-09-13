package com.manish.articlelisting.repository

import com.manish.articlelisting.article.datasource.ArticleDataSourceRemote
import com.manish.articlelisting.article.model.ArticleItem
import io.reactivex.Single
import javax.inject.Inject

class ArticleRepositoryManager @Inject constructor(
    private val remoteDataSource: ArticleDataSourceRemote
) : ArticleRepository {

    override fun getArticles(page: Int, perPage: Int): Single<List<ArticleItem>> {
        return remoteDataSource.getArticlesByPage(page, perPage)
    }
}