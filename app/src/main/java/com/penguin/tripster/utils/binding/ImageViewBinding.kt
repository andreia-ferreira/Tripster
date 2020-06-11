package com.penguin.tripster.utils.binding

import android.graphics.drawable.ColorDrawable
import android.widget.ImageView

import androidx.databinding.BindingAdapter

import com.bumptech.glide.Glide
import com.penguin.tripster.R

object ImageViewBinding {

    @JvmStatic
    @BindingAdapter("android:insertFromUrl")
    fun setImageUrl(view: ImageView, url: String) {
        Glide.with(view.context)
            .asBitmap()
            .load(url)
            .placeholder(ColorDrawable(view.resources.getColor(R.color.placeholderImageColor)))
            .error(ColorDrawable(view.resources.getColor(R.color.placeholderImageColor)))
            .thumbnail(0.3f)
            .into(view)
    }

}
