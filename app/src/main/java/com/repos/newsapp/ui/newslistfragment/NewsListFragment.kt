package com.repos.newsapp.ui.newslistfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        }
    }

    private fun setUpObserver() {
        viewModel.articlesList.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resources.Loading -> {
                    if (response.isLoading) binding.pbNews.visibility = View.VISIBLE
                    else binding.pbNews.visibility = View.GONE
                }
                is Resources.Success -> {
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                    binding.pbNews.visibility = View.GONE
                    binding.rvNews.visibility = View.VISIBLE
                }
                is Resources.Error -> {
                    response.data?.let { newsResponse ->
                        Snackbar.make(binding.rvNews, newsResponse.message.toString(), LENGTH_LONG)
                            .show()
                    }
                    binding.pbNews.visibility = View.GONE
                    binding.rvNews.visibility = View.VISIBLE
                }
            }
        })
    }
}