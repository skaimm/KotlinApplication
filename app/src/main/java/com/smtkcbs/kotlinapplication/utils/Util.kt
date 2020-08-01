package com.smtkcbs.kotlinapplication.utils

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.smtkcbs.kotlinapplication.R


fun ImageView.downloadImage(url : String?){

    val option = RequestOptions().placeholder(createPlacedolder(context)).error(R.mipmap.ic_launcher_round)

    Glide.with(context).setDefaultRequestOptions(option).load(url).into(this)
}

fun createPlacedolder(context : Context) : CircularProgressDrawable {

    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 45f
        start()
    }
}

@BindingAdapter("android:downloadImage")
fun downloadImageXML( view: ImageView , url: String?){
    view.downloadImage(url)
}