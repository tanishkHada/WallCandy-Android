package com.example.wallcandy.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wallcandy.BaseApplication
import com.example.wallcandy.R
import com.example.wallcandy.adapters.SearchAdapter
import com.example.wallcandy.databinding.ActivitySearchBinding
import com.example.wallcandy.utilities.StatusBarManager
import com.example.wallcandy.viewmodels.SearchViewModel
import com.example.wallcandy.viewmodels.ViewModelFactory

class SearchActivity : AppCompatActivity() {
    private var bind : ActivitySearchBinding? = null
    private lateinit var viewModel : SearchViewModel
    private lateinit var searchAdapter: SearchAdapter
    private var previousQuery = ""
    private var emptyEditable = Editable.Factory.getInstance().newEditable("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        StatusBarManager.setStatusBarColorAndContentsColor(this, R.color.bg)

        bind = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(bind!!.root)

        initializeRecyclerView()
        initializeViewModel()
        setupListeners()

        bind!!.searchBar.requestFocus()
    }

    private fun initializeRecyclerView(){
        searchAdapter = SearchAdapter(this)
        bind!!.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            adapter = searchAdapter
            itemAnimator = null
        }
    }

    private fun initializeViewModel(){
        val wallpaperRepository = (this.application as BaseApplication).wallpaperRepository

        viewModel = ViewModelProvider(this, ViewModelFactory(wallpaperRepository))[SearchViewModel::class.java]
        viewModel.searchedWallpapers.observe(this, Observer{
            searchAdapter.differ.submitList(it)

            if(it.isNotEmpty()){
                bind!!.initialLoader.visibility = View.GONE
                bind!!.contentLayout.visibility = View.VISIBLE
            }

            bind!!.loadMoreSpinner.visibility = View.GONE
            bind!!.loadMoreButton.visibility = View.VISIBLE
        })
    }

    private fun setupListeners(){
        bind!!.searchBar.setOnEditorActionListener { textView, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_NEXT ||
                actionId == EditorInfo.IME_ACTION_GO ||
                (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER)) {

                // Handle the Enter key action here
                if(!bind!!.searchBar.text.isNullOrEmpty() && !bind!!.searchBar.text.isNullOrBlank() &&
                    bind!!.searchBar.text.toString() != previousQuery) {

                    viewModel.clearSearchResults()

                    searchAdapter.differ.submitList(null) {
                        searchAdapter.differ.submitList(emptyList())
                    }

                    bind!!.initialLoader.visibility = View.VISIBLE
                    bind!!.contentLayout.visibility = View.GONE

                    viewModel.getSearchedWallpapers(bind!!.searchBar.text.toString())

                    previousQuery = bind!!.searchBar.text.toString()
                }

                // Optionally, hide the keyboard if needed
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(bind!!.searchBar.windowToken, 0)

                bind!!.searchBar.clearFocus()

                true
            } else
                false
        }

        bind!!.searchBar.setOnFocusChangeListener { view, b ->
            if(b)
                bind!!.cancelText.visibility = View.VISIBLE
            else
                bind!!.cancelText.visibility = View.GONE
        }

        bind!!.cancelText.setOnClickListener {
            bind!!.searchBar.text = emptyEditable
        }

        bind!!.loadMoreButton.setOnClickListener {
            bind!!.loadMoreButton.visibility = View.GONE
            bind!!.loadMoreSpinner.visibility = View.VISIBLE

            viewModel.getSearchedWallpapers(bind!!.searchBar.text.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bind = null
        viewModel.clearSearchResults()
    }
}