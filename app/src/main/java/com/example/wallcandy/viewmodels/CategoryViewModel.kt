package com.example.wallcandy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallcandy.models.Wallpaper
import com.example.wallcandy.repository.WallpaperRepository
import com.example.wallcandy.utilities.MyConstants
import kotlinx.coroutines.launch

class CategoryViewModel(private val wallpaperRepository: WallpaperRepository) : ViewModel() {

    //fetch trending wallpapers as soon as viewmodel object is created
    init {
        viewModelScope.launch {
            wallpaperRepository.getCategoryWallpapers(MyConstants.CATEGORY_LIST, 1, 1)
        }
    }

    val categoryWallpapers: LiveData<List<Wallpaper>>
        get() = wallpaperRepository.categoryWallpapers
}