package com.prueba.themovieGS.ui.home.adapters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.prueba.themovieGS.base.BaseViewHolder
import com.prueba.themovieGS.data.entity.Movie
import com.prueba.themovieGS.databinding.ItemGalleryBinding
import com.prueba.themovieGS.ui.home.adapters.holder.MoviesViewHolder
import com.prueba.themovieGS.ui.home.adapters.repository.OnMovieListener

class HomeAdapter(
    private val moviesList: List<Movie>,
    private val itemClickListener: OnMovieListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            ItemGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = MoviesViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            val position =
                holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener
            itemClickListener.onMovieClick(moviesList[position])
        }
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MoviesViewHolder -> holder.bind(moviesList[position])
        }
    }

    override fun getItemCount(): Int = moviesList.size
}
