package com.prueba.themovieGS.ui.home.adapters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prueba.themovieGS.base.BaseConcatHolder
import com.prueba.themovieGS.databinding.PopularMoviesRowBinding

class NowPlayingAdapter(private val moviesAdapter: HomeAdapter) :
    RecyclerView.Adapter<BaseConcatHolder<*>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseConcatHolder<*> {
        val itemBinding =
            PopularMoviesRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConcatViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseConcatHolder<*>, position: Int) {
        when (holder) {
            is ConcatViewHolder -> holder.bind(moviesAdapter)
        }
    }

    override fun getItemCount(): Int = 1

    private inner class ConcatViewHolder(val binding: PopularMoviesRowBinding) :
        BaseConcatHolder<HomeAdapter>(binding.root) {
        override fun bind(adapter: HomeAdapter) {
            binding.rvPopularMovies.adapter = adapter
        }
    }
}
