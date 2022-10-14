package com.prueba.themovieGS.ui.home.adapters.holder

import com.prueba.themovieGS.base.BaseConcatHolder
import com.prueba.themovieGS.databinding.UpcomingMoviesRowBinding
import com.prueba.themovieGS.ui.home.adapters.adapter.HomeAdapter

class UpcomingViewHolder(val binding: UpcomingMoviesRowBinding) :
    BaseConcatHolder<HomeAdapter>(binding.root) {
    override fun bind(adapter: HomeAdapter) {
        binding.rvUpcommingMovies.adapter = adapter
    }
}
