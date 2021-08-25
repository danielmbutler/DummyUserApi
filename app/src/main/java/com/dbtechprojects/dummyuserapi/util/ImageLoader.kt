package com.dbtechprojects.dummyuserapi.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.dbtechprojects.dummyuserapi.R

object ImageLoader {

    fun loadImage(context: Context, ImageView: ImageView, url: String){
        Glide.with(context)
            .load(url)
            .override(SIZE_ORIGINAL, SIZE_ORIGINAL)
            .placeholder(R.drawable.ic_launcher_background)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .error(R.drawable.ic_launcher_background)
            .into(ImageView)
    }


}