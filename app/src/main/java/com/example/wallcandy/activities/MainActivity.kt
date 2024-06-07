package com.example.wallcandy.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.wallcandy.R
import com.example.wallcandy.adapters.MyFragmentPageAdapter
import com.example.wallcandy.databinding.ActivityMainBinding
import com.example.wallcandy.fragments.CategoryFragment
import com.example.wallcandy.fragments.RandomFragment
import com.example.wallcandy.fragments.TrendingFragment
import com.example.wallcandy.utilities.StatusBarManager
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private var bind : ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        StatusBarManager.setStatusBarColorAndContentsColor(this, R.color.bg)

        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind!!.root)

        setupFragmentViewPager()
        setupListeners()
    }

    private fun setupFragmentViewPager(){
        val fragmentList = listOf<Fragment>(TrendingFragment(), RandomFragment(), CategoryFragment())
        val fragmentPagerAdapter = MyFragmentPageAdapter(fragmentList, supportFragmentManager, lifecycle)
        bind!!.viewPager.adapter = fragmentPagerAdapter

        TabLayoutMediator(bind!!.tabLayout, bind!!.viewPager){tab, position ->
            when(position){
                0 -> tab.text = "Trending"
                1 -> tab.text = "Random"
                2 -> tab.text = "Category"
            }
        }.attach()
    }

    private fun setupListeners(){
        bind!!.btnSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bind = null
    }
}