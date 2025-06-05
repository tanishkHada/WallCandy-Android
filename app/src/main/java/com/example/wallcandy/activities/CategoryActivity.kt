package com.example.wallcandy.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wallcandy.BaseApplication
import com.example.wallcandy.R
import com.example.wallcandy.adapters.SearchAdapter
import com.example.wallcandy.databinding.ActivityCategoryBinding
import com.example.wallcandy.utilities.MyConstants
import com.example.wallcandy.utilities.StatusBarManager
import com.example.wallcandy.viewmodels.SearchViewModel
import com.example.wallcandy.viewmodels.ViewModelFactory

class CategoryActivity : AppCompatActivity() {
    private var bind : ActivityCategoryBinding? = null
    private lateinit var viewModel : SearchViewModel
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var category : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        StatusBarManager.setStatusBarColorAndContentsColor(this, R.color.bg)

        bind = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(bind!!.root)

        category = intent.getStringExtra(MyConstants.CATEGORY)!!

        bind!!.title.text = category

        initializeRecyclerView()
        initializeViewModel()
        setupListeners()
    }

    private fun initializeRecyclerView(){
        searchAdapter = SearchAdapter(this)
        bind!!.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            adapter = searchAdapter
        }
    }

    private fun initializeViewModel(){
        val wallpaperRepository = (this.application as BaseApplication).wallpaperRepository

        viewModel = ViewModelProvider(this, ViewModelFactory(wallpaperRepository))[SearchViewModel::class.java]
        viewModel.searchedWallpapers.observe(this, Observer{
            if(it.isNotEmpty()){
                bind!!.initialLoader.visibility = View.GONE
                bind!!.contentLayout.visibility = View.VISIBLE
            }
            searchAdapter.differ.submitList(it)

            bind!!.loadMoreSpinner.visibility = View.GONE
            bind!!.loadMoreButton.visibility = View.VISIBLE
        })

        viewModel.getSearchedWallpapers(category)
    }

    private fun setupListeners(){
        bind!!.btnBack.setOnClickListener {
            finish()
        }

        bind!!.loadMoreButton.setOnClickListener {
            bind!!.loadMoreButton.visibility = View.GONE
            bind!!.loadMoreSpinner.visibility = View.VISIBLE

            viewModel.getSearchedWallpapers(category)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bind = null
        viewModel.clearSearchResults()
    }
}