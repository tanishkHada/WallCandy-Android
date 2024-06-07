package com.example.wallcandy.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wallcandy.BaseApplication
import com.example.wallcandy.R
import com.example.wallcandy.adapters.CategoryAdapter
import com.example.wallcandy.adapters.TrendingAdapter
import com.example.wallcandy.databinding.FragmentCategoryBinding
import com.example.wallcandy.databinding.FragmentTrendingBinding
import com.example.wallcandy.utilities.MyConstants
import com.example.wallcandy.viewmodels.CategoryViewModel
import com.example.wallcandy.viewmodels.TrendingViewModel
import com.example.wallcandy.viewmodels.ViewModelFactory

class CategoryFragment : Fragment() {
    private var bind : FragmentCategoryBinding? = null
    private lateinit var viewModel : CategoryViewModel
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentCategoryBinding.inflate(layoutInflater, container, false)
        return bind!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()
        initializeViewModel()
    }

    private fun initializeRecyclerView(){
        categoryAdapter = CategoryAdapter(requireContext(), MyConstants.CATEGORY_LIST)
        bind!!.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = categoryAdapter
        }
    }

    private fun initializeViewModel(){
        val wallpaperRepository = (requireActivity().application as BaseApplication).wallpaperRepository

        viewModel = ViewModelProvider(this, ViewModelFactory(wallpaperRepository))[CategoryViewModel::class.java]
        viewModel.categoryWallpapers.observe(viewLifecycleOwner, Observer{
            categoryAdapter.differ.submitList(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bind = null
    }
}