package com.example.wallcandy.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wallcandy.api.WallpaperApiService
import com.example.wallcandy.models.Wallpaper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WallpaperRepository(private val apiService: WallpaperApiService) {

    private val trendingWallpapersLiveData = MutableLiveData<List<Wallpaper>>()
    private val randomWallpapersLiveData = MutableLiveData<List<Wallpaper>>()
    private val searchedWallpapersLiveData = MutableLiveData<List<Wallpaper>>()
    private val categoryWallpapersLiveData = MutableLiveData<List<Wallpaper>>()

    val trendingWallpapers: LiveData<List<Wallpaper>>
        get() = trendingWallpapersLiveData
    val randomWallpapers: LiveData<List<Wallpaper>>
        get() = randomWallpapersLiveData
    val searchedWallpapers: LiveData<List<Wallpaper>>
        get() = searchedWallpapersLiveData
    val categoryWallpapers: LiveData<List<Wallpaper>>
        get() = categoryWallpapersLiveData

    suspend fun getTrendingWallpapers(page: Int, per_page: Int) {
        try {
            val result = apiService.getTrendingWallpapers(page, per_page)
            if (result.isSuccessful && result.body() != null) {
                val newWallpapers = result.body()!!.photos
                val current = trendingWallpapersLiveData.value ?: emptyList()
                trendingWallpapersLiveData.postValue(current + newWallpapers)
            } else {

            }
        } catch (e: Exception) {
            Log.e("API_DEBUG", "Exception during API call: ${e.message}", e)
        }
    }

    suspend fun getRandomWallpapers(page: Int, per_page: Int) {
        try {
            val result = apiService.getRandomWallpapers(page, per_page)
            if (result.isSuccessful && result.body() != null) {
                val newWallpapers = result.body()!!.photos
                val current = randomWallpapersLiveData.value ?: emptyList()
                randomWallpapersLiveData.postValue(current + newWallpapers)
            } else {

            }
        } catch (e: Exception) {
            Log.e("API_DEBUG", "Exception during API call: ${e.message}", e)
        }
    }

    suspend fun getSearchedWallpapers(query: String, page: Int, per_page: Int) {
        try {
            val result = apiService.getSearchedWallpapers(query, page, per_page)
            if (result.isSuccessful && result.body() != null) {
                val newWallpapers = result.body()!!.photos
                val current = searchedWallpapersLiveData.value ?: emptyList()
                searchedWallpapersLiveData.postValue(current + newWallpapers)
            } else {

            }
        } catch (e: Exception) {
            Log.e("API_DEBUG", "Exception during API call: ${e.message}", e)
        }
    }

    //getting a list of single wallpapers for each category
    suspend fun getCategoryWallpapers(queries: List<String>, page: Int, per_page: Int) {
        try {
            val wallpapers = mutableListOf<Wallpaper>()
            for (query in queries) {
                val result = apiService.getSearchedWallpapers(query, page, per_page)
                if (result.isSuccessful) {
                    if (result?.body() != null) {
                        wallpapers.add(result.body()!!.photos[0])
                        // Use withContext to ensure that updates happen on the main thread
                        withContext(Dispatchers.Main) {
                            categoryWallpapersLiveData.value = ArrayList(wallpapers)
                        }
                    }
                } else {

                }
            }
        } catch (e: Exception) {
            Log.e("API_DEBUG", "Exception during API call: ${e.message}", e)
        }
    }

    fun clearSearchResults() {
        searchedWallpapersLiveData.value = emptyList()
    }
}