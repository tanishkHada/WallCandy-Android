package com.example.wallcandy

import android.app.Application
import com.example.wallcandy.api.RetrofitHelper
import com.example.wallcandy.api.WallpaperApiService
import com.example.wallcandy.repository.WallpaperRepository
import com.example.wallcandy.utilities.MyToast
import com.example.wallcandy.utilities.NetworkUtils

class BaseApplication : Application() {
    lateinit var wallpaperRepository: WallpaperRepository

    override fun onCreate() {
        super.onCreate()

        if(!NetworkUtils.isNetworkAvailable(this))
            MyToast.showToastShort("No Internet Connection", this)

        initializeRepository()
    }

    private fun initializeRepository(){
        val wallpaperApiService = RetrofitHelper.getInstance().create(WallpaperApiService::class.java)
        wallpaperRepository = WallpaperRepository(wallpaperApiService)
    }
}