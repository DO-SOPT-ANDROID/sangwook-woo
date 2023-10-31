package org.sopt.dosopttemplate.util.binding

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.RoundedCornersTransformation
import org.sopt.dosopttemplate.R
import java.time.LocalDate

@BindingAdapter("Music")
fun TextView.Music(music: String?) {
    if (music == null) {
        visibility = View.GONE
    } else {
        text = music
        visibility = View.VISIBLE
    }
}

@BindingAdapter("Music")
fun LinearLayout.Music(music: String?) {
    if (music == null) {
        visibility = View.GONE
    } else {
        visibility = View.VISIBLE
    }
}

@BindingAdapter("setImageUrl")
fun ImageView.setImageUrl(imageUrl: String?) {
    if (imageUrl == null) {
        load(R.drawable.img_profile)
    } else {
        load(imageUrl) {
            placeholder(R.drawable.img_profile)
            transformations(RoundedCornersTransformation(10F))
        }
    }
}

@BindingAdapter("birthDay")
fun View.birthDay(birthday: LocalDate?) {
    if(birthday?.monthValue == LocalDate.now().monthValue && birthday.dayOfMonth == LocalDate.now().dayOfMonth) {
        visibility = View.VISIBLE
    }else{
        visibility = View.GONE
    }
}

@BindingAdapter("setBirthday")
fun TextView.setBirthday(birthday: LocalDate?) {
    this.text = resources.getString(R.string.friend_detail_birthday,birthday?.monthValue,birthday?.dayOfMonth)
}
