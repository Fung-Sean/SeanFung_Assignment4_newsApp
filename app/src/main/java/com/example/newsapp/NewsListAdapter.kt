package com.example.newsapp
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.ListNewsBinding
import com.example.newsapp.NewsApiService.News

class NewsListAdapter : ListAdapter<News, NewsHolder>(NewsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListNewsBinding.inflate(inflater, parent, false)
        return NewsHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
    }
}

class NewsHolder(private val binding: ListNewsBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(news: News) {
        binding.newsTitle.text = news.title

        binding.root.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "${news.title} clicked",
                Toast.LENGTH_SHORT
            ).show()

        }
    }
}

class NewsDiffCallback : DiffUtil.ItemCallback<News>() {
    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem.title == newItem.title // Change this to a unique identifier for your news item
    }

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem == newItem
    }
}