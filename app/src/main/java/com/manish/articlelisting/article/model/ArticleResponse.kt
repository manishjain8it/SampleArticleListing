package com.manish.articlelisting.article.model

data class ArticleItem(
    val comments: Long,
    val content: String,
    val createdAt: String,
    val id: String,
    val likes: Long,
    val media: List<Media>,
    val user: List<User>
)

data class Media(
    val blogId: String,
    val createdAt: String,
    val id: String,
    val image: String,
    val title: String,
    val url: String
)

data class User(
    val about: String,
    val avatar: String,
    val blogId: String,
    val city: String,
    val createdAt: String,
    val designation: String,
    val id: String,
    val lastname: String,
    val name: String
)