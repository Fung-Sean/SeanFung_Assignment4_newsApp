package com.example.newsapp
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.ListNewsBinding
import com.example.newsapp.NewsApiService.News
import android.view.View
import androidx.navigation.Navigation

class NewsListAdapter(
    private val navController: NavController,
    private val onItemClick: (News) -> Unit
) : ListAdapter<News, NewsHolder>(NewsDiffCallback()) {
    interface OnNewsItemClickListener {
        fun onItemClick(news: News)
    }
    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
        holder.itemView.setOnClickListener {
            onItemClick(currentItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListNewsBinding.inflate(inflater, parent, false)
        return NewsHolder(binding)
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