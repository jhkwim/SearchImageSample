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
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.jhkim.core.common.Util.fromDpToPx
import com.jhkim.core.model.ImageWithFavoriteStatus
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

        val imageAdapter = ImageResourceAdapter { imageWithFavoriteStatus ->
            imageOnClick(imageWithFavoriteStatus)
        }

        binding.searchList.apply {
            val layoutManager = GridLayoutManager(context, 4)
            this.layoutManager = layoutManager
            addItemDecoration(ImageItemDecoration(spanCount = 4, spacing = 16f.fromDpToPx()))
            adapter = imageAdapter
            addOnScrollListener(object : OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (viewModel.isLoading.value || viewModel.isLastPage.value) return

                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    // 스크롤이 맨 아래로 내려왔을 때 추가 데이터 로딩

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        // 여기에서 추가 데이터를 로드하는 작업을 수행하세요.
                        // 예를 들어, 네트워크 요청을 보내거나 데이터베이스에서 추가 데이터를 가져올 수 있습니다.
                        Log.i("jhkim", "loadMore")
                        viewModel.searchMore()
                    }
                }
            })
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.searchedImages.collect {
                    imageAdapter.submitList(it)
                }
            }
        }

        return binding.root
    }

    private fun imageOnClick(imageWithFavoriteStatus: ImageWithFavoriteStatus) {
        if (imageWithFavoriteStatus.isFavorite) {
            viewModel.removeFavoriteImage(imageWithFavoriteStatus.imageResource)
        } else {
            viewModel.addFavoriteImage(imageWithFavoriteStatus.imageResource)
        }
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