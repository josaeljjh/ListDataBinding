package com.hdev.food.utils.extensions

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.transform.BlurTransformation
import coil.transform.CircleCropTransformation
import coil.transform.GrayscaleTransformation
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.hdev.food.R


//Fondos de servicio
@BindingAdapter("imageURL3")
fun ImageView.setBackgroundUrl(id: String) {
    try {
        val options = RequestOptions()
            .placeholder(R.mipmap.ic_launcher)
            .priority(Priority.NORMAL)
            .format(DecodeFormat.PREFER_RGB_565)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .dontAnimate()
            .fitCenter()

        Glide.with(this)
            .asBitmap()
            .load(id)
            .apply(options)
            .into(this)
    } catch (e: Exception) {
    }
}

@BindingAdapter("image")
fun ImageView.setBackground(id: Int) {
    try {
        val options = RequestOptions()
            .placeholder(R.mipmap.ic_launcher)
            .priority(Priority.NORMAL)
            .format(DecodeFormat.PREFER_RGB_565)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .dontAnimate()

        Glide.with(this)
            .asBitmap()
            .load(id)
            .apply(options)
            .into(this)
    } catch (e: Exception) {
    }
}

@BindingAdapter("imageBlur")
fun ImageView.setBackgroundBlur(id: Int) {
    this.load(id) {
        crossfade(true)
        placeholder(R.mipmap.ic_launcher)
        //transformations(BlurTransformation(context,3f))
        //transformations(GrayscaleTransformation())
    }
}

@BindingAdapter("imageURL")
fun ImageView.setBgURL(id: String) {
        this.load(id) {
            crossfade(true)
            placeholder(R.drawable.ic_image)
            transformations(CircleCropTransformation())
            //transformations(BlurTransformation(context,3f))
            //transformations(GrayscaleTransformation())
        }
}
