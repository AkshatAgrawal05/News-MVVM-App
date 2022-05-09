package com.repos.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.repos.newsapp.data.model.Articles

@Database(
    entities = [Articles::class],
    version = 1
)
@TypeConverters(SourceConverter::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun getNewsDao(): NewsDao
}