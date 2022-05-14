package com.repos.newsapp.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import jp.wasabeef.glide.transformations.BlurTransformation

class ImageAdapter {

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, url: String?) {
            if (url != null)
                Glide.with(view.context)
                    .load(url)
                    .transform(BlurTransformation())
                    .into(view)
        }
    }

}