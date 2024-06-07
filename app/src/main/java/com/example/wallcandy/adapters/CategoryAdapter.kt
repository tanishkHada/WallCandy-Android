package com.example.wallcandy.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallcandy.activities.CategoryActivity
import com.example.wallcandy.databinding.CategoryItemBinding
import com.example.wallcandy.models.Wallpaper
import com.example.wallcandy.utilities.MyConstants

class CategoryAdapter(private val context: Context, private val categoryList : List<String>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(var itemBind: CategoryItemBinding) :
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide.with(context).load(differ.currentList[position].src.landscape).centerCrop().into(holder.itemBind.imageView)
        holder.itemBind.title.text = categoryList[position]

        holder.itemBind.imageView.setOnClickListener {
            val intent = Intent(context, CategoryActivity::class.java).apply {
                putExtra(MyConstants.CATEGORY, categoryList[position])
            }
            context.startActivity(intent)
        }
    }
}