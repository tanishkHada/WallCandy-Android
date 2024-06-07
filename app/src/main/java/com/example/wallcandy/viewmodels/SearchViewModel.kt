package com.example.wallcandy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallcandy.models.Wallpaper
import com.example.wallcandy.repository.WallpaperRepository
import kotlinx.coroutines.launch

class SearchViewModel(private val wallpaperRepository: WallpaperRepository) : ViewModel() {

    val searchedWallpapers: LiveData<List<Wallpaper>>
        get() = wallpaperRepository.searchedWallpapers

    fun getSearchedWallpapers(query : String, page : Int, per_page : Int){
        viewModelScope.launch {
            wallpaperRepository.getSearchedWallpapers(query, page, per_page)
        }
    }

    fun clearSearchResults(){
        wallpaperRepository.clearSearchResults()
    }
}