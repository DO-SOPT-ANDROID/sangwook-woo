package org.sopt.dosopttemplate.util.activity

import android.app.Activity
import android.view.View
import org.sopt.dosopttemplate.util.context.hideKeyboard

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}