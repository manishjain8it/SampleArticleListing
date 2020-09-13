package com.manish.articlelisting.di

import com.manish.articlelisting.BuildConfig
import com.manish.articlelisting.article.datasource.ArticleDataSourceRemote
import com.manish.articlelisting.common.Constants
import com.manish.articlelisting.repository.ApiService
import com.manish.articlelisting.repository.ArticleRepository
import com.manish.articlelisting.repository.ArticleRepositoryManager
import com.manish.articleslistingsample.article.usecase.GetArticleListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.apply { loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY }
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideArticleDataSourceRemote(apiService: ApiService)= ArticleDataSourceRemote(apiService)

    @Provides
    @Singleton
    fun provideArticleRepository(articleDataSourceRemote: ArticleDataSourceRemote): ArticleRepository {
        return ArticleRepositoryManager(articleDataSourceRemote)
    }

    @Provides
    @Singleton
    fun provideGetArticleListUseCase(articleRepository: ArticleRepository)= GetArticleListUseCase(articleRepository)

}