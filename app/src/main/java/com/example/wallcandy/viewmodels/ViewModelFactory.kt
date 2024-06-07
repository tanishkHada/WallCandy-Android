package com.example.wallcandy.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wallcandy.repository.WallpaperRepository

class ViewModelFactory(
    private val wallpaperRepository: WallpaperRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(TrendingViewModel::class.java) -> TrendingViewModel(wallpaperRepository)
            modelClass.isAssignableFrom(RandomViewModel::class.java) -> RandomViewModel(wallpaperRepository)
            modelClass.isAssignableFrom(CategoryViewModel::class.java) -> CategoryViewModel(wallpaperRepository)
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> SearchViewModel(wallpaperRepository)
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        } as T
    }
}
