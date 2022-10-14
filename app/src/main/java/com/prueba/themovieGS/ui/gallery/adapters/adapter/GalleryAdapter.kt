package com.prueba.themovieGS.ui.gallery.adapters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prueba.themovieGS.base.BaseViewHolder
import com.prueba.themovieGS.data.entity.ImageList
import com.prueba.themovieGS.databinding.ImagesItemViewBinding
import com.prueba.themovieGS.ui.gallery.adapters.holder.GalleryViewHolder

class GalleryAdapter(private val images: List<ImageList>) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemViewBinding =
            ImagesItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryViewHolder(itemViewBinding, parent.context)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is GalleryViewHolder -> holder.bind(images[position])
        }
    }

    override fun getItemCount(): Int = images.size
}
