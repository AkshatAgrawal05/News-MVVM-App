package com.repos.newsapp.ui.newslistfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import com.repos.newsapp.databinding.FragmentNewsListBinding
import com.repos.newsapp.domain.repository.NewsRepository
import com.repos.newsapp.util.Resources
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsListFragment : Fragment() {
    private lateinit var binding: FragmentNewsListBinding
    private lateinit var newsAdapter: NewsListAdapter

    @Inject
    lateinit var repository: NewsRepository

    private val viewModel: NewsListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsListBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        viewModel.getNewsList()
        setUpObserver()
    }

    private fun setUpRecyclerView() {
        newsAdapter = NewsListAdapter()
        binding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            addOnScrollListener(this@NewsListFragment.scrollListener)
        }
    }

    private fun setUpObserver() {
        viewModel.articlesList.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resources.Loading -> {
                    isLoading = response.isLoading
                    if (response.isLoading) binding.pbNews.visibility = View.VISIBLE
                    else binding.pbNews.visibility = View.GONE
                }
                is Resources.Success -> {
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                        val totalPages = newsResponse.totalResults?.div(10)?.plus(2)
                        isLastPage = viewModel.newsPage == totalPages
                    }
                    binding.pbNews.visibility = View.GONE
                    binding.rvNews.visibility = View.VISIBLE
                    isLoading = false
                }
                is Resources.Error -> {
                    response.data?.let { newsResponse ->
                        Snackbar.make(binding.rvNews, newsResponse.message.toString(), LENGTH_LONG)
                            .show()
                    }
                    binding.pbNews.visibility = View.GONE
                    binding.rvNews.visibility = View.VISIBLE
                    isLoading = false
                }
            }
        })
    }

    var isLoading = false
    var isScrolling = false
    var isLastPage = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= 10
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getNewsList()
                isScrolling = false
            }
        }
    }
}