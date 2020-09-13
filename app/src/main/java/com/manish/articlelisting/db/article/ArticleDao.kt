package com.manish.articlelisting.db.article

import androidx.room.*
import io.reactivex.Single


@Dao
interface ArticleDao {

    @Query("SELECT COUNT(id) FROM articleData")
    fun getRowCount(): Int

    @Query("SELECT * FROM articleData LIMIT :limit OFFSET :offset")
    fun getArticlesByPage(offset: Int, limit: Int): Single<List<ArticleDataEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data: ArticleDataEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(data: ArticleDataEntity)

    @Transaction
    fun upsert(data: ArticleDataEntity) {
        val id = insert(data)
        if (id == -1L) {
            update(data)
        }
    }
}