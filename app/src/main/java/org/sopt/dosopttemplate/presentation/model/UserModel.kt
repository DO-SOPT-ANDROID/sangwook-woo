package org.sopt.dosopttemplate.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.sopt.dosopttemplate.domain.entity.User

@Parcelize
data class UserModel(
    val id: String? = "",
    val pw: String? = "",
    val nickname: String? = "",
    val discription: String? = ""
) : Parcelable {
    companion object {
        fun toUserModel(user: User?): UserModel {
            return UserModel(user?.id, user?.pw, user?.nickname, user?.discription)
        }
    }
    fun toUserModel(user: User?): UserModel {
        return UserModel(user?.id, user?.pw, user?.nickname, user?.discription)
    }

    fun toUser(): User {
        return User(id, pw, nickname, discription)
    }
}
