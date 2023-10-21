package org.sopt.dosopttemplate.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String = "",
    val pw: String = "",
    val nickname: String = "",
    val hobby: String = ""
) : Parcelable
