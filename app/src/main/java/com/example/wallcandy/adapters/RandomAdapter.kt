package com.example.wallcandy.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallcandy.activities.WallpaperSelectActivity
import com.example.wallcandy.databinding.WallpaperItemBinding
import com.example.wallcandy.models.Wallpaper
import com.example.wallcandy.utilities.MyConstants

class RandomAdapter(private val context: Context) :
    RecyclerView.Adapter<RandomAdapter.RandomViewHolder>() {

    class RandomViewHolder(var itemBind: WallpaperItemBinding) :
        RecyclerView.ViewHolder(itemBind.root)

    //diff callback mechanism to efficiently update contents of the list without re-binding all the items
    //when dataset changes
    private val diffCallback = object : DiffUtil.ItemCallback<Wallpaper>() {
        override fun areItemsTheSame(oldItem: Wallpaper, newItem: Wallpaper): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Wallpaper, newItem: Wallpaper): Boolean {
            return true
        }
    }
    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RandomViewHolder {
        val view = WallpaperItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RandomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RandomViewHolder, position: Int) {
        Glide.with(context).load(differ.currentList[position].src.portrait).centerCrop().into(holder.itemBind.imageView)
        holder.itemBind.imageView.setOnClickListener {
            val intent = Intent(context, WallpaperSelectActivity::class.java).apply {
                putExtra(MyConstants.INDEX, position)
                putParcelableArrayListExtra(MyConstants.LIST, ArrayList(differ.currentList))
            }
            context.startActivity(intent)
        }
    }
}