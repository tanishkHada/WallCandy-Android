package com.example.wallcandy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallcandy.models.Wallpaper
import com.example.wallcandy.repository.WallpaperRepository
import kotlinx.coroutines.launch

class RandomViewModel(private val wallpaperRepository: WallpaperRepository) : ViewModel(){

    private var currPage = 0
    private val perPage = 15
    private var nextLoading = false

    //fetch random wallpapers as soon as viewmodel object is created
    init{
        loadRandomWallpapers()
    }

    val randomWallpapers : LiveData<List<Wallpaper>>
        get() = wallpaperRepository.randomWallpapers

    fun loadRandomWallpapers(){
        if(nextLoading){
            return
        }
        currPage++
        nextLoading = true
        viewModelScope.launch {
            wallpaperRepository.getRandomWallpapers(currPage, perPage)
            nextLoading = false
        }
    }
}