package com.example.wallcandy.models

data class PexelResponse(
    val photos: List<Wallpaper>,
    val page: Int,
    val per_page: Int,
    val total_results: Int,
    val next_page: String
)