package com.repos.newsapp.domain.repository

import com.repos.newsapp.data.model.NewsResponse
import com.repos.newsapp.util.Resources

interface NewsRepository {

    suspend fun getTopHeadlineNews(
        pageSize: Int? = null,
        pageNum: Int? = null
    ): Resources<NewsResponse>
}