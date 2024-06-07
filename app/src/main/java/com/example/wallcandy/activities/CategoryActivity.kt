package com.example.wallcandy.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            searchAdapter.differ.submitList(it)
        })

        viewModel.getSearchedWallpapers(category, 1, 50)
    }

    private fun setupListeners(){
        bind!!.btnBack.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bind = null
        viewModel.clearSearchResults()
    }
}