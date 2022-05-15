package com.repos.newsapp.ui.newslistfragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.repos.newsapp.data.model.NewsResponse
import com.repos.newsapp.domain.repository.NewsRepository
import com.repos.newsapp.util.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    val articlesList: MutableLiveData<Resources<NewsResponse>> = MutableLiveData()
    var newsPage = 1

    fun getNewsList() {
        viewModelScope.launch {
            articlesList.postValue(Resources.Loading(true))
            val response = repository.getTopHeadlineNews(pageNum = newsPage)
            newsPage++
            articlesList.postValue(response)
//            articlesList.postValue(Resources.Loading(false))
        }
    }
}