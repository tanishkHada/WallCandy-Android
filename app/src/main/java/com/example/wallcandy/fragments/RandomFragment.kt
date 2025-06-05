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
import com.example.wallcandy.adapters.RandomAdapter
import com.example.wallcandy.databinding.FragmentRandomBinding
import com.example.wallcandy.viewmodels.RandomViewModel
import com.example.wallcandy.viewmodels.ViewModelFactory

class RandomFragment : Fragment() {
    private var bind : FragmentRandomBinding? = null
    private lateinit var viewModel : RandomViewModel
    private lateinit var randomAdapter: RandomAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bind = FragmentRandomBinding.inflate(layoutInflater, container, false)
        return bind!!.root 
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()
        initializeViewModel()
        setLoadMoreButton()
    }

    private fun initializeRecyclerView(){
        randomAdapter = RandomAdapter(requireContext())
        bind!!.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            adapter = randomAdapter
        }
    }

    private fun initializeViewModel(){
        val wallpaperRepository = (requireActivity().application as BaseApplication).wallpaperRepository

        viewModel = ViewModelProvider(this, ViewModelFactory(wallpaperRepository))[RandomViewModel::class.java]
        viewModel.randomWallpapers.observe(viewLifecycleOwner, Observer{
            if(it.isNotEmpty()){
                bind!!.initialLoader.visibility = View.GONE
                bind!!.contentLayout.visibility = View.VISIBLE
            }
            randomAdapter.differ.submitList(it)

            bind!!.loadMoreSpinner.visibility = View.GONE
            bind!!.loadMoreButton.visibility = View.VISIBLE
        })
    }

    private fun setLoadMoreButton(){
        bind!!.loadMoreButton.setOnClickListener {
            bind!!.loadMoreButton.visibility = View.GONE
            bind!!.loadMoreSpinner.visibility = View.VISIBLE

            viewModel.loadRandomWallpapers()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bind = null
    }
}