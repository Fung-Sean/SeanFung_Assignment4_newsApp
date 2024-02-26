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
    ): View? {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)

        binding.newsRecyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = NewsListAdapter() // Create adapter instance without passing data
        binding.newsRecyclerView.adapter = adapter

        // Observe the LiveData in the ViewModel
        viewModel.news.observe(viewLifecycleOwner) { newsList ->
            adapter.submitList(newsList)
        }

        // Call the API test function
        //ApiTest().testApiCall()

        return binding.root
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total news: ${viewModel.news.value?.size ?: 0}" )
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}