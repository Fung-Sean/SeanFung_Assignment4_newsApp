package com.example.newsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.FragmentNewsDetailBinding
import com.example.newsapp.databinding.FragmentNewsListBinding

private const val TAG = "DetailFragment"
class News_Detail_Fragment : Fragment(){
    private var _binding : FragmentNewsDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding){
            "Cannot access binding because it is null. Is view visible?"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Retrieve individual parameters from arguments
        val title = arguments?.getString("title")
        Log.d(TAG,"title is ${title}")
        val description = arguments?.getString("description")
        val content = arguments?.getString("content")
        val imageUrl = arguments?.getString("imageUrl")

        // Now you can use these parameters to update your UI
        title?.let { binding.newsTitle.text = it }
        description?.let { binding.newsDescription.text = it }
        content?.let { binding.newsContent.text = it }
        imageUrl?.let {
            Glide.with(this)
                .load(it)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.newsImage)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
