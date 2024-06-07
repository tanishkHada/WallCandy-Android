package com.example.wallcandy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallcandy.databinding.WallpaperPagerItemBinding
import com.example.wallcandy.models.Wallpaper

class WallpaperPagerAdapter(
    private val wallpaperList: List<Wallpaper>,
    private val context: Context
) : RecyclerView.Adapter<WallpaperPagerAdapter.WallpaperViewHolder>() {

    inner class WallpaperViewHolder(val itemBind: WallpaperPagerItemBinding) :
        RecyclerView.ViewHolder(itemBind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewHolder {
        val view = WallpaperPagerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WallpaperViewHolder(view)
    }

    override fun getItemCount(): Int {
        return wallpaperList.size
    }

    override fun onBindViewHolder(holder: WallpaperViewHolder, position: Int) {
        Glide.with(context).load(wallpaperList[position].src.portrait).centerCrop()
            .into(holder.itemBind.imageView)
    }
}