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

    var newsResponse: NewsResponse? = null

    override suspend fun getTopHeadlineNews(
        pageSize: Int?,
        pageNum: Int?
    ): Resources<NewsResponse> {
        return try {
            val response = api.getTopNewsHeadline(pageSize = pageSize, pageNum = pageNum)
            val body = response.body()
            if (response.isSuccessful) {
                if (newsResponse == null) {
                    newsResponse = body
                } else {
                    val oldArticle = newsResponse?.articles
                    val newArticles = body?.articles
                    if (newArticles != null) {
                        oldArticle?.addAll(newArticles)
                    }
                }
                Resources.Success(data = newsResponse ?: body)
            } else {
                Resources.Error(data = body, message = response.message())
            }
        } catch (e: Exception) {
            Resources.Error(message = e.message)
        }
    }
}