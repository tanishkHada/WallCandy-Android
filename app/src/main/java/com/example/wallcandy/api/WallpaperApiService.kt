package com.example.wallcandy.api

import com.example.wallcandy.models.PexelResponse
import com.example.wallcandy.models.Wallpaper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WallpaperApiService {
    //fetch popular for trending, and curated for random.

    @GET("popular")
    suspend fun getTrendingWallpapers(
        @Query("page") page : Int,
        @Query("per_page") per_page : Int
    ) : Response<PexelResponse>

    @GET("curated")
    suspend fun getRandomWallpapers(
        @Query("page") page : Int,
        @Query("per_page") per_page : Int
    ) : Response<PexelResponse>

    @GET("search")
    suspend fun getSearchedWallpapers(
        @Query("query") query : String,
        @Query("page") page : Int,
        @Query("per_page") per_page : Int
    ) : Response<PexelResponse>
}