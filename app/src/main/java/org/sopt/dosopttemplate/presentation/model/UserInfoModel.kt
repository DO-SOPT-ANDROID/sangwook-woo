package org.sopt.dosopttemplate.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.sopt.dosopttemplate.domain.entity.ReqresUser

@Parcelize
data class UserInfoModel(
    val id: Int?,
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val avatar: String?
): Parcelable {
    companion object {

        fun toUserInfoModel(user : ReqresUser): UserInfoModel = UserInfoModel(
            id = user.id,
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            avatar = user.avatar
        )

        fun toReqresUser(userInfoList: List<UserInfoModel>) = userInfoList.map { data ->
            ReqresUser(
                id = data.id,
                firstName = data.firstName,
                lastName = data.lastName,
                email = data.email,
                avatar = data.avatar
            )
        }
    }
}
