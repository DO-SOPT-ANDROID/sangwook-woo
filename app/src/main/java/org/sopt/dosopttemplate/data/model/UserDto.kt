package org.sopt.dosopttemplate.data.model

import kotlinx.serialization.Serializable
import org.sopt.dosopttemplate.domain.entity.User

@Serializable
data class UserDto(
    val id: String? = "",
    val pw: String? = "",
    val nickname: String? = "",
    val hobby: String? = ""
) {
    fun toUser(): User? {
        return User(id, pw, nickname, hobby)
    }

    fun toUserDto(user: User?): UserDto {
        return UserDto(user?.id, user?.pw, user?.nickname, user?.hobby)
    }
}
