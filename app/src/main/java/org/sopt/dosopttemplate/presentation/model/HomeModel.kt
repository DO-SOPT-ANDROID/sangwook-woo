package org.sopt.dosopttemplate.presentation.model

import androidx.annotation.DrawableRes
import java.time.LocalDate

sealed class HomeModel {
    data class MyInfoModel(
        val name: String?,
        val discription: String?,
    ) : HomeModel() {
        companion object {
            const val MY_INFO_VIEW_TYPE = 0
        }
    }

    data class FriendInfoModel(
        val id: Int,
        val name: String,
        val birthday: LocalDate,
        val music: String?,
        @DrawableRes val image: Int? = null,
    ) : HomeModel() {
        companion object {
            const val NORMAL_FRIEND_VIEW_TYPE = 1
            const val BIRTHDAY_FRIEND_VIEW_TYPE = 2
        }
    }
}
