package org.sopt.dosopttemplate.data.model.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestLoginDto(
    @SerialName("username")
    val userName : String? = "",
    @SerialName("password")
    val password : String? = ""
)
