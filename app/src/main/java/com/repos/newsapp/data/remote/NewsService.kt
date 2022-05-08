package com.repos.newsapp.data.remote

import com.repos.newsapp.data.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    companion object {
        const val BASE_URL = "https://newsapi.org/"
        const val API_KEY = "ea07d374abf14deeab095b6fd81d50f2"
    }

    @GET("v2/top-headlines")
    suspend fun getTopNewsHeadline(
        @Query("apiKey") api_key: String = API_KEY,
        @Query("pageSize") pageSize: Int? = null,
        @Query("page") pageNum: Int? = null
    ): Response<NewsResponse>
}