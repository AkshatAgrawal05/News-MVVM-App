package com.repos.newsapp.ui.newslistfragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.repos.newsapp.domain.repository.NewsRepository
import com.repos.newsapp.util.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {
    fun getNewsList() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val res = repository.getTopHeadlineNews()) {
                is Resources.Success -> Log.d("res", res.data.toString())
                is Resources.Error -> Log.d("res", "${res.data.toString()} ${res.message}")
                is Resources.Loading -> Log.d("res", res.isLoading.toString())
            }
        }
    }
}