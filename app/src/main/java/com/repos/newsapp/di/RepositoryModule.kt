package com.repos.newsapp.di

import com.repos.newsapp.data.repository.NewsRepositoryImpl
import com.repos.newsapp.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun getRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository
}