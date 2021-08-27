package com.jmm.brsap.norogi

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(url:String){
    Glide
        .with(context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.ic_baseline_image_24)
        .into(this)
}