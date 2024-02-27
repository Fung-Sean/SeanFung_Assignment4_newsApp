package com.example.newsapp
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import com.example.newsapp.databinding.FragmentNewsListBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

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
        Log.d(TAG, "Entered View")
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        val navController = findNavController()
        Log.d(TAG, "findNavController")
        val adapter = NewsListAdapter(navController, this::onItemClick)
        Log.d(TAG,"Left View")
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(requireContext()) // Set the layout manager
        binding.newsRecyclerView.adapter = adapter
        // Observe the LiveData in the ViewModel
        viewModel.news.observe(viewLifecycleOwner) { newsList ->
            adapter.submitList(newsList)
        }

        return binding.root
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
