package com.manish.articlelisting.article.datasource

import com.manish.articlelisting.article.model.ArticleItem
import com.manish.articlelisting.repository.ApiService
import io.reactivex.Single

class ArticleDataSourceRemote(private val apiService: ApiService) : ArticleDataSource {
    override fun getArticlesByPage(page: Int, perPage: Int): Single<List<ArticleItem>> {
        return apiService.getArticleListByPaging(page = page.toString(), limit = perPage.toString())
    }

    override fun saveAllArticles(articles: List<ArticleItem>): Single<List<ArticleItem>> {
        TODO("Not yet implemented")
    }
}