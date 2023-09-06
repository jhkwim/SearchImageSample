package com.jhkim.kko.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.RoundedCornersTransformation

object ImageItemBindingAdapter {

    @BindingAdapter("load_coil")
    @JvmStatic fun loadCoil(view: ImageView, value: String) {
        view.load(value) {
            crossfade(true)
            transformations(RoundedCornersTransformation(20f))
        }
    }

}