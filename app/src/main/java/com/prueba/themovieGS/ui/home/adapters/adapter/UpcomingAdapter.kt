package com.prueba.themovieGS.ui.home.adapters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prueba.themovieGS.base.BaseConcatHolder
import com.prueba.themovieGS.databinding.UpcomingMoviesRowBinding
import com.prueba.themovieGS.ui.home.adapters.holder.UpcomingViewHolder

class UpcomingAdapter(private val moviesAdapter: HomeAdapter) :
    RecyclerView.Adapter<BaseConcatHolder<*>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseConcatHolder<*> {
        val itemBinding =
            UpcomingMoviesRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UpcomingViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseConcatHolder<*>, position: Int) {
        when (holder) {
            is UpcomingViewHolder -> holder.bind(moviesAdapter)
        }
    }

    override fun getItemCount(): Int = 1
}
