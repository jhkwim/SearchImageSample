package com.jhkim.kko.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jhkim.core.common.Util.fromDpToPx
import com.jhkim.core.model.ImageResource
import com.jhkim.kko.R
import com.jhkim.kko.databinding.FragmentFavoriteBinding
import com.jhkim.kko.ui.ImageResourceAdapter
import com.jhkim.kko.ui.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        val imageAdapter = ImageResourceAdapter { imageWithFavoriteStatus ->
            imageOnClick(binding.root, imageWithFavoriteStatus.imageResource)
        }

        binding.favoriteList.apply {
            layoutManager = GridLayoutManager(context, 4)
            addItemDecoration(
                SearchFragment.ImageItemDecoration(
                    spanCount = 4,
                    spacing = 16f.fromDpToPx()
                )
            )
            adapter = imageAdapter
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoriteImages.collect {
                    imageAdapter.submitList(it)
                }
            }
        }

        return binding.root
    }

    private fun imageOnClick(rootView: View, imageResource: ImageResource) {
        Snackbar.make(rootView, R.string.message_remove_favorite_image, Snackbar.LENGTH_SHORT)
            .setAction(R.string.confirm) {
                viewModel.removeFavoriteImage(imageResource)
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}