package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.model.SearchItem

class MovieAdapter(
    private val list: List<SearchItem?>?,
    val click: (listItem: SearchItem) -> Unit
) : Adapter<MovieAdapter.MyViewHolder>() {


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieTitle: TextView = itemView.findViewById(R.id.tv_title)
        val movieYear: TextView = itemView.findViewById(R.id.tv_year)
        val moviePoster: ImageView = itemView.findViewById(R.id.iv_poster)

        init {
            itemView.setOnClickListener {
                click.invoke(list?.get(adapterPosition)!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflate: LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.row_movie_card, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.apply {
            movieTitle.text = list?.get(position)?.title
            movieYear.text = list?.get(position)?.year

            Glide.with(holder.itemView.context)
                .load(list?.get(position)?.poster)
                .placeholder(R.drawable.placeholder_image)
                .into(holder.moviePoster)
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }
}