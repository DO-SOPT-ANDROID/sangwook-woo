package org.sopt.dosopttemplate.data.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseLoginDto(
    @SerialName("id")
    val id : Int,
    @SerialName("username")
    val userName : String,
    @SerialName("nickname")
    val nickname : String
)
