package org.sopt.dosopttemplate.data.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sopt.dosopttemplate.domain.entity.User

@Serializable
data class ResponseLoginDto(
    @SerialName("id")
    val id : Int,
    @SerialName("username")
    val userName : String,
    @SerialName("nickname")
    val nickname : String,
    @SerialName("message")
    val message : String? = "",
) {
    fun toUser(): User? {
        return User( userName, null , nickname, null)
    }
}
