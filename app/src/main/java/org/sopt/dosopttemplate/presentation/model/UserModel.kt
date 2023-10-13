package org.sopt.dosopttemplate.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.sopt.dosopttemplate.domain.entity.User

@Parcelize
data class UserModel(
    val id: String? = "",
    val pw: String? = "",
    val nickname: String? = "",
    val hobby: String? = ""
) : Parcelable {
    fun toUserModel(user: User?): UserModel {
        return UserModel(user?.id, user?.pw, user?.nickname, user?.hobby)
    }

    fun toUser(): User {
        return User(id, pw, nickname, hobby)
    }
}
