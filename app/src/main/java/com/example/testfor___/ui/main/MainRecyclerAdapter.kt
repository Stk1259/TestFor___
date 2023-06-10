package com.example.testfor___.ui.main

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.testfor___.R
import com.example.testfor___.data.Category

class MainRecyclerAdapter(private val context: Context, var categories: List<Category>) :
    RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>() {
    private val layoutInflator = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflator.inflate(R.layout.item_category, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val getItem = categories[position]
        holder.categoryName.text = getItem.name
        Glide.with(context)
            .load(getItem.image_url)
            .skipMemoryCache(true)
            .into(holder.background)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.textCategory)
        val background: ImageView = itemView.findViewById(R.id.imageBackground)
        var itemPosition = 0

        init {
            itemView.setOnClickListener {
//                val intent = Intent(context, ::class.java)
//                context.startActivity(intent)
            }
        }
    }
}