package org.sopt.dosopttemplate.util.binding

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("Music")
fun TextView.Music(music: String?) {
    if(music == null){
        visibility = View.GONE
    }else{
        text = music
        visibility = View.VISIBLE
    }
}