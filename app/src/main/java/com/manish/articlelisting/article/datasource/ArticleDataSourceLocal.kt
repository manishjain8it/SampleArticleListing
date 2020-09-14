package com.manish.articlelisting.article.datasource

import com.manish.articlelisting.article.model.ArticleItem
import com.manish.articlelisting.article.model.Media
import com.manish.articlelisting.article.model.User
import com.manish.articlelisting.db.article.ArticleDao
import com.manish.articlelisting.db.article.ArticleDataEntity
import com.manish.articlelisting.db.article.MediaData
import com.manish.articlelisting.db.article.UserData
import io.reactivex.Single

class ArticleDataSourceLocal(private val articleDao: ArticleDao) : ArticleDataSource {
    override fun getArticlesByPage(page: Int, perPage: Int): Single<List<ArticleItem>> {

        return articleDao.getArticlesByPage(page, perPage)
            .map { data -> convertToArticleItem(data) }
    }

    override fun saveAllArticles(articles: List<ArticleItem>): Single<List<ArticleItem>> {
        val articleEntityList = convertToArticleDataEntityItem(articles)
        for(article in articleEntityList){
            articleDao.upsert(article)
        }
        return Single.just(articles)
    }

    private fun convertToArticleItem(data: List<ArticleDataEntity>): List<ArticleItem> {
        return data.map { item ->
            ArticleItem(
                id = item.id,
                comments = item.comments,
                content = item.content,
                createdAt = item.createdAt,
                likes = item.likes,
                media = convertToMediaItem(item.media),
                user = convertToUserItem(item.user)
            )
        }
    }

    private fun convertToMediaItem(data: List<MediaData>): List<Media> {
        return data.map { item ->
            Media(
                id = item.id,
                blogId = item.blogId,
                createdAt = item.createdAt,
                image = item.image,
                url = item.url,
                title = item.title
            )
        }
    }

    private fun convertToUserItem(data: List<UserData>): List<User> {
        return data.map { item ->
            User(
                id = item.id,
                blogId = item.blogId,
                createdAt = item.createdAt,
                about = item.about,
                avatar = item.avatar,
                city = item.city,
                designation = item.designation,
                name = item.name,
                lastname = item.lastname
            )
        }
    }

    private fun convertToArticleDataEntityItem(data: List<ArticleItem>): List<ArticleDataEntity> {
        return data.map { item ->
            ArticleDataEntity(
                id = item.id,
                comments = item.comments,
                content = item.content,
                createdAt = item.createdAt,
                likes = item.likes,
                media = convertToMediaData(item.media),
                user = convertToUserData(item.user)
            )
        }
    }

    private fun convertToMediaData(data: List<Media>): ArrayList<MediaData> {
        return data.map { item ->
            MediaData(
                id = item.id,
                blogId = item.blogId,
                createdAt = item.createdAt,
                image = item.image,
                url = item.url,
                title = item.title
            )
        } as ArrayList<MediaData>
    }

    private fun convertToUserData(data: List<User>): ArrayList<UserData> {
        return data.map { item ->
            UserData(
                id = item.id,
                blogId = item.blogId,
                createdAt = item.createdAt,
                about = item.about,
                avatar = item.avatar,
                city = item.city,
                designation = item.designation,
                name = item.name,
                lastname = item.lastname
            )
        } as ArrayList<UserData>
    }

}