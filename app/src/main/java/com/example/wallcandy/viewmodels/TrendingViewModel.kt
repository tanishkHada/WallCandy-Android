package com.example.wallcandy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallcandy.models.Wallpaper
import com.example.wallcandy.repository.WallpaperRepository
import kotlinx.coroutines.launch

class TrendingViewModel(private val wallpaperRepository: WallpaperRepository) : ViewModel() {

    private var currPage = 0
    private val perPage = 15
    private var nextLoading = false

    //fetch trending wallpapers as soon as viewmodel object is created
    init {
        loadTrendingWallpapers()
    }

    val trendingWallpapers: LiveData<List<Wallpaper>>
        get() = wallpaperRepository.trendingWallpapers

    fun loadTrendingWallpapers(){
        if(nextLoading){
            return
        }
        currPage++
        nextLoading = true
        viewModelScope.launch {
            wallpaperRepository.getTrendingWallpapers(currPage, perPage)
            nextLoading = false
        }
    }
}