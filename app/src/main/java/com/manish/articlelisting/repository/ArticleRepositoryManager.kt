package com.manish.articlelisting.repository

import com.manish.articlelisting.article.datasource.ArticleDataSourceLocal
import com.manish.articlelisting.article.datasource.ArticleDataSourceRemote
import com.manish.articlelisting.article.model.ArticleItem
import com.manish.articlelisting.util.NetworkHelper
import io.reactivex.Single
import javax.inject.Inject

class ArticleRepositoryManager @Inject constructor(private val localDataSource: ArticleDataSourceLocal,
                                                   private val remoteDataSource : ArticleDataSourceRemote,
                                                   private val networkHelper: NetworkHelper
) : ArticleRepository {

    override fun getArticles(page: Int, perPage: Int): Single<List<ArticleItem>> {
        if(networkHelper.isNetworkConnected()){
            val remote = remoteDataSource.getArticlesByPage(page, perPage)
                .flatMap { localDataSource.saveAllArticles(it)
                }
            return remote
        }else {
            return localDataSource.getArticlesByPage((page-1) * perPage, perPage)
        }
    }
}