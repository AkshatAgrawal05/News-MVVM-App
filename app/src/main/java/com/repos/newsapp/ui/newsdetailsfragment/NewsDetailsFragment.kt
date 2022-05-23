package com.repos.newsapp.ui.newsdetailsfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.repos.newsapp.data.model.Articles
import com.repos.newsapp.databinding.FragmentNewsDetailsBinding


class NewsDetailsFragment : Fragment() {

    private var articles: Articles? = null
    private lateinit var binding: FragmentNewsDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        bundle?.let { bun ->
            articles = bun.getParcelable<Articles>("article")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsDetailsBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articles?.let {
            binding.article = it
        }
    }
}