package com.prueba.themovieGS.ui.gallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.prueba.themovieGS.R
import com.prueba.themovieGS.base.Resources
import com.prueba.themovieGS.data.remote.PhotoScreenDataSource
import com.prueba.themovieGS.databinding.FragmentGalleryListBinding
import com.prueba.themovieGS.domain.PhotoScreenRepoImpl
import com.prueba.themovieGS.ui.gallery.viewmodel.GalleryViewModel
import com.prueba.themovieGS.ui.gallery.viewmodel.PhotoScreenViewModelFactory
import com.prueba.themovieGS.ui.gallery.adapters.adapter.GalleryAdapter

class GalleryListFragment : Fragment(R.layout.fragment_gallery_list) {

    private lateinit var binding: FragmentGalleryListBinding
    private val viewModel by viewModels<GalleryViewModel> {
        PhotoScreenViewModelFactory(
            PhotoScreenRepoImpl(
                PhotoScreenDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGalleryListBinding.bind(view)

        viewModel.fetchLatestImages().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resources.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resources.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvImages.adapter = GalleryAdapter(result.data)
                }

                is Resources.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}
