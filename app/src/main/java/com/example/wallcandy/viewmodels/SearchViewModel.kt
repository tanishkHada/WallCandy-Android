package com.example.wallcandy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallcandy.models.Wallpaper
import com.example.wallcandy.repository.WallpaperRepository
import kotlinx.coroutines.launch

class SearchViewModel(private val wallpaperRepository: WallpaperRepository) : ViewModel() {

    private var currPage = 0
    private val perPage = 15
    private var nextLoading = false

    val searchedWallpapers: LiveData<List<Wallpaper>>
        get() = wallpaperRepository.searchedWallpapers

    fun getSearchedWallpapers(query : String){
        if(nextLoading){
            return
        }
        currPage++
        nextLoading = true
        viewModelScope.launch {
            wallpaperRepository.getSearchedWallpapers(query, currPage, perPage)
            nextLoading = false
        }
    }

    fun clearSearchResults(){
        wallpaperRepository.clearSearchResults()
    }
}