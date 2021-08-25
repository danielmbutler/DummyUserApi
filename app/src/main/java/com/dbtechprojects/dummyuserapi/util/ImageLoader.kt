package com.dbtechprojects.dummyuserapi.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL

object ImageLoader {

    fun loadImage(context: Context, ImageView: ImageView, url: String, placeholder: Int){
        Glide.with(context)
            .load(url)
            .override(SIZE_ORIGINAL, SIZE_ORIGINAL)
            .placeholder(placeholder)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .error(placeholder)
            .into(ImageView);
    }


}