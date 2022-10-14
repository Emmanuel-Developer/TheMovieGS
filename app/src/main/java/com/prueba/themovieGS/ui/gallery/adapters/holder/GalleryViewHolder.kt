package com.prueba.themovieGS.ui.gallery.adapters.holder

import android.content.Context
import com.bumptech.glide.Glide
import com.prueba.themovieGS.R
import com.prueba.themovieGS.base.BaseViewHolder
import com.prueba.themovieGS.data.entity.ImageList
import com.prueba.themovieGS.databinding.ImagesItemViewBinding
import kotlin.random.Random

class GalleryViewHolder(
    val binding: ImagesItemViewBinding,
    val context: Context
) : BaseViewHolder<ImageList>(binding.root) {
    override fun bind(item: ImageList) {
        val time = Random.nextInt(0, 60)
        binding.timeElapsed.text = context.getString(R.string.time, time.toString())
        Glide.with(context).load(item.imageUrl).centerCrop().into(binding.post)
    }
}
