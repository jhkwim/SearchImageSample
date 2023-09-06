package com.jhkim.kko.ui.search

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.jhkim.core.common.Util
import com.jhkim.core.common.Util.fromDpToPx
import com.jhkim.core.model.ImageResource
import com.jhkim.kko.R
import com.jhkim.kko.databinding.FragmentSearchBinding
import com.jhkim.kko.ui.ImageResourceAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate<FragmentSearchBinding?>(
            inflater,
            R.layout.fragment_search,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            this.viewModel = this@SearchFragment.viewModel
        }

        val imageAdapter = ImageResourceAdapter { imageResource ->
            imageOnClick(binding.root, imageResource)
        }

        binding.searchList.apply {
            layoutManager = GridLayoutManager(context, 4)
            addItemDecoration(ImageItemDecoration(spanCount = 4, spacing = 16f.fromDpToPx()))
            adapter = imageAdapter
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.searchedImages.collect {
                    imageAdapter.submitList(it)
                }
            }
        }

        return binding.root
    }

    private fun imageOnClick(rootView: View, imageResource: ImageResource) {
        Log.i("jhkim", "on click : $imageResource")
        Snackbar.make(rootView, "즐겨찾기", Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class ImageItemDecoration(
        private val spanCount: Int, // Grid의 column 수
        private val spacing: Int // 간격
    ) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position: Int = parent.getChildAdapterPosition(view)

            if (position >= 0) {
                val column = position % spanCount // item column
                outRect.apply {
                    // spacing - column * ((1f / spanCount) * spacing)
                    left = spacing - column * spacing / spanCount
                    // (column + 1) * ((1f / spanCount) * spacing)
                    right = (column + 1) * spacing / spanCount
                    if (position < spanCount) top = spacing
                    bottom = spacing
                }
            } else {
                outRect.apply {
                    left = 0
                    right = 0
                    top = 0
                    bottom = 0
                }
            }
        }

    }
}