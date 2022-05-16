package com.repos.newsapp.data.local

import androidx.room.TypeConverter
import com.repos.newsapp.data.model.Source

class SourceConverter {
    @TypeConverter
    fun fromSource(source: Source): String? {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}