package com.example.newsapp

import NewsListAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView // Import correct SearchView class
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.newsapp.databinding.FragmentNewsListBinding
import androidx.recyclerview.widget.LinearLayoutManager

private const val TAG = "NewsListFragment"

class NewsListFragment : Fragment() {
    private var _binding: FragmentNewsListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val viewModel: NewsListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)

        val adapter = NewsListAdapter(this::onItemClick)

        binding.newsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.newsRecyclerView.adapter = adapter

        viewModel.news.observe(viewLifecycleOwner) { newsList ->
            adapter.submitList(newsList)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize searchView
        val searchView = view.findViewById<SearchView>(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.fetchNews(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Optional: Handle text changes in the searchView
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun onItemClick(news: NewsApiService.News) {
        val bundle = Bundle().apply {
            putString("title", news.title)
            putString("description", news.description)
            putString("urlToImage", news.urlToImage)
            putString("content", news.content)
            putString("author", news.author)
        }
        findNavController().navigate(R.id.show_news_detail, bundle)
    }
}
