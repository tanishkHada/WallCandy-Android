package com.example.wallcandy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallcandy.models.Wallpaper
import com.example.wallcandy.repository.WallpaperRepository
import kotlinx.coroutines.launch

class TrendingViewModel(private val wallpaperRepository: WallpaperRepository) : ViewModel() {

    //fetch trending wallpapers as soon as viewmodel object is created
    init {
        viewModelScope.launch {
            wallpaperRepository.getTrendingWallpapers(1, 50)
        }
    }

    val trendingWallpapers: LiveData<List<Wallpaper>>
        get() = wallpaperRepository.trendingWallpapers
}