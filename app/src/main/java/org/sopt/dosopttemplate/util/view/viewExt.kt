package org.sopt.dosopttemplate.util.view

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.snackBar(message: () -> String) {
    Snackbar.make(this, message(), Snackbar.LENGTH_SHORT).show()
}