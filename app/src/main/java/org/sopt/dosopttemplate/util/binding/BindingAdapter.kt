package org.sopt.dosopttemplate.util.binding

import android.util.TypedValue
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import coil.load
import coil.transform.RoundedCornersTransformation
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.domain.entity.ValidationResult
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
    if (birthday?.monthValue == LocalDate.now().monthValue && birthday.dayOfMonth == LocalDate.now().dayOfMonth) {
        visibility = View.VISIBLE
    } else {
        visibility = View.GONE
    }
}

@BindingAdapter("setBirthday")
fun TextView.setBirthday(birthday: LocalDate?) {
    this.text = resources.getString(
        R.string.friend_detail_birthday,
        birthday?.monthValue,
        birthday?.dayOfMonth
    )
}

@BindingAdapter("validateId")
fun TextView.validateId(validationResult: ValidationResult?) {
    if (validationResult != null) {
        this.text = validationResult.errorMessage
        when (validationResult.successful) {
            true -> {}
            false -> {
                this.setTextColor(ContextCompat.getColor(this.context, R.color.red050))
            }
        }
    }
}

@BindingAdapter("validatePw")
fun TextView.validatePw(validationResult: ValidationResult?) {
    if (validationResult != null) {
        this.text = validationResult.errorMessage
        when (validationResult.successful) {
            true -> {}
            false -> {
                this.setTextColor(ContextCompat.getColor(this.context, R.color.red050))
            }
        }
    }
}

@BindingAdapter("validateSignup")
fun Button.validateSignup(signupValidation: Boolean) {
    val themedContext = ContextThemeWrapper(context, R.style.Theme_DoSoptTemplate)

    val typedValue = TypedValue()
    themedContext.theme.resolveAttribute(android.R.attr.colorPrimary, typedValue, true)
    val colorPrimaryResId = typedValue.resourceId

    val errorColor = R.color.gray050

    val colorStateList = if (signupValidation) {
        getColorStateList(context, colorPrimaryResId)
    } else {
        getColorStateList(context, errorColor)
    }

    this.backgroundTintList = colorStateList
    this.isEnabled = signupValidation
}

@BindingAdapter("focus")
fun EditText.setFocus(focus: Boolean) {
    if (focus) {
        this.requestFocus()
    } else {
        this.clearFocus()
    }
}

@InverseBindingAdapter(attribute = "focus", event = "focusChange")
fun EditText.getFocus(): Boolean {
    return isFocused
}

@BindingAdapter("focusChange")
fun EditText.focusChange(inverseBindingListener: InverseBindingListener?) {
    this.setOnFocusChangeListener { _, hasFocus ->
        inverseBindingListener?.onChange()
    }
}

@BindingAdapter("validVisibility")
fun TextView.validVisibility(focus: Boolean) {
    if (focus) {
        this.isVisible = true
    } else {
        this.isInvisible = true
    }
}