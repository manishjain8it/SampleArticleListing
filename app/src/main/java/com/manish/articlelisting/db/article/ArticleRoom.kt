package com.manish.articlelisting.db.article

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "articleData")
data class ArticleDataEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "comments")
    val comments: Long,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "created_at")
    val createdAt: String,
    @ColumnInfo(name = "likes")
    val likes: Long,
    @ColumnInfo(name = "media_list")
    @TypeConverters(MediaDataListTypeConverter::class)
    val media: ArrayList<MediaData>,
    @ColumnInfo(name = "user_list")
    @TypeConverters(UserDataListTypeConverter::class)
    val user: ArrayList<UserData>
)

data class MediaData(
    val blogId: String,
    val createdAt: String,
    val id: String,
    val image: String,
    val title: String,
    val url: String
)

data class UserData(
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