package com.repos.newsapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.repos.newsapp.data.model.Articles

@Dao
interface NewsDao {

    @Query("SELECT * FROM NewsArticles")
    suspend fun getAllSavedArticles(): LiveData<List<Articles>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArticle(articles: Articles): Long

    @Delete
    suspend fun deleteArticle(articles: Articles)

}