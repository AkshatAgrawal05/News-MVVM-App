package com.repos.newsapp.ui.newslistfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.repos.newsapp.R
import com.repos.newsapp.data.model.Articles
import com.repos.newsapp.databinding.NewsListItemBinding

class NewsListAdapter : RecyclerView.Adapter<NewsListAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(private val binding: NewsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(articles: Articles?) {
            binding.article = articles
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Articles>() {
        override fun areItemsTheSame(oldItem: Articles, newItem: Articles): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Articles, newItem: Articles): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: NewsListItemBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.news_list_item,
            parent,
            false
        )

        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}