package org.sopt.dosopttemplate.data.model.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sopt.dosopttemplate.domain.entity.User

@Serializable
data class RequestLoginDto(
    @SerialName("username")
    val userName : String?,
    @SerialName("password")
    val password : String?
) {
    companion object {
        fun toReqLoginDto(user: User): RequestLoginDto{
            return RequestLoginDto(user.id, user.pw)
        }
    }
}
