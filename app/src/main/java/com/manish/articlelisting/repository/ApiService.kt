package com.manish.articlelisting.repository

import com.manish.articlelisting.article.model.ArticleItem
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("api/v1/blogs")
    fun getArticleListByPaging(
        @Query("page") page: String,
        @Query("limit") limit: String
    ): Single<List<ArticleItem>>
}