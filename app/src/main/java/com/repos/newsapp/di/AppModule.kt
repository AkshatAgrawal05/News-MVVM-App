package com.repos.newsapp.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.repos.newsapp.data.local.NewsDao
import com.repos.newsapp.data.local.NewsDatabase
import com.repos.newsapp.data.remote.NewsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun getGson(): Gson = GsonBuilder().create()

    @Provides
    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLogging = HttpLoggingInterceptor()
        httpLogging.level = HttpLoggingInterceptor.Level.BODY
        return httpLogging
    }

    @Singleton
    @Provides
    fun getOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun getNewsApiService(gson: Gson, okHttpClient: OkHttpClient): NewsService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(NewsService.BASE_URL)
        .client(okHttpClient)
        .build()
        .create(NewsService::class.java)

    @Singleton
    @Provides
    fun getNewsDb(app: Application): NewsDatabase {
        return Room.databaseBuilder(
            app,
            NewsDatabase::class.java,
            "News.db"
        ).build()
    }

    @Provides
    @Singleton
    fun getNewsAppDao(db: NewsDatabase): NewsDao = db.getNewsDao()
}