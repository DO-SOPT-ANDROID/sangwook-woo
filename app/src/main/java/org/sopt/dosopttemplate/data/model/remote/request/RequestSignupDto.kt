package org.sopt.dosopttemplate.data.model.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sopt.dosopttemplate.domain.entity.User

@Serializable
data class RequestSignupDto(
    @SerialName("username")
    val username : String?,
    @SerialName("password")
    val password : String?,
    @SerialName("nickname")
    val nickname : String?
){
    companion object{
        fun toReqSignupDto(user: User): RequestSignupDto{
            return RequestSignupDto(user.id, user.pw, user.nickname)
        }
    }
}
