package com.example.wallcandy.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wallcandy.BaseApplication
import com.example.wallcandy.adapters.TrendingAdapter
import com.example.wallcandy.databinding.FragmentTrendingBinding
import com.example.wallcandy.viewmodels.TrendingViewModel
import com.example.wallcandy.viewmodels.ViewModelFactory

class TrendingFragment : Fragment() {
    private var bind : FragmentTrendingBinding? = null
    private lateinit var viewModel : TrendingViewModel
    private lateinit var trendingAdapter: TrendingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bind = FragmentTrendingBinding.inflate(layoutInflater, container, false)
        return bind!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()
        initializeViewModel()
    }

    private fun initializeRecyclerView(){
        trendingAdapter = TrendingAdapter(requireContext())
        bind!!.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            adapter = trendingAdapter
        }
    }

    private fun initializeViewModel(){
        val wallpaperRepository = (requireActivity().application as BaseApplication).wallpaperRepository

        viewModel = ViewModelProvider(this, ViewModelFactory(wallpaperRepository))[TrendingViewModel::class.java]
        viewModel.trendingWallpapers.observe(viewLifecycleOwner, Observer{
            trendingAdapter.differ.submitList(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bind = null
    }
}