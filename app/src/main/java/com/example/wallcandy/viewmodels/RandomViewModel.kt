package com.example.wallcandy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallcandy.models.Wallpaper
import com.example.wallcandy.repository.WallpaperRepository
import kotlinx.coroutines.launch

class RandomViewModel(private val wallpaperRepository: WallpaperRepository) : ViewModel(){

    //fetch random wallpapers as soon as viewmodel object is created
    init{
        viewModelScope.launch {
            wallpaperRepository.getRandomWallpapers(1, 50)
        }
    }

    val randomWallpapers : LiveData<List<Wallpaper>>
        get() = wallpaperRepository.randomWallpapers

}