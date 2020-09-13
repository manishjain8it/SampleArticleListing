package com.manish.articlelisting.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.manish.articlelisting.db.article.ArticleDao
import com.manish.articlelisting.db.article.ArticleDataEntity
import com.manish.articlelisting.db.article.MediaDataListTypeConverter
import com.manish.articlelisting.db.article.UserDataListTypeConverter

@Database(entities = [ArticleDataEntity::class], version = 1, exportSchema = false)
@TypeConverters(UserDataListTypeConverter::class, MediaDataListTypeConverter::class)
abstract class ArticleDatabase : RoomDatabase(){

    abstract fun articleDao(): ArticleDao

    companion object {
        const val DATABASE_NAME = "article.db"
    }
}