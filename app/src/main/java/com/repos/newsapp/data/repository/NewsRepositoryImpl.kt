package com.repos.newsapp.data.repository

import com.repos.newsapp.data.local.NewsDao
import com.repos.newsapp.data.model.NewsResponse
import com.repos.newsapp.data.remote.NewsService
import com.repos.newsapp.domain.repository.NewsRepository
import com.repos.newsapp.util.Resources
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val dao: NewsDao,
    private val api: NewsService
) : NewsRepository {

    override suspend fun getTopHeadlineNews(
        pageSize: Int?,
        pageNum: Int?
    ): Resources<NewsResponse> {
        return try {
            val response = api.getTopNewsHeadline(pageSize = pageSize, pageNum = pageNum)
            val body = response.body()
            if (response.isSuccessful) {
                Resources.Success(data = body)
            } else {
                Resources.Error(data = body, message = response.message())
            }
        } catch (e: Exception) {
            Resources.Error(message = e.message)
        }
    }
}