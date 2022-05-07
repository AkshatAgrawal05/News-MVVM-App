package com.repos.newsapp.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.repos.newsapp.data.local.NewsDao
import com.repos.newsapp.data.local.NewsDatabase
import com.repos.newsapp.data.remote.NewsService
import com.repos.newsapp.data.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun getGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun getNewsApiService(gson: Gson): NewsService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(NewsService.BASE_URL)
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

    @Provides
    @Singleton
    fun getRepository(dao: NewsDao, api: NewsService) = NewsRepositoryImpl(dao, api)
}