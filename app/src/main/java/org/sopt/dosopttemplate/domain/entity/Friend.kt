package org.sopt.dosopttemplate.domain.entity

import java.time.LocalDate

data class Friend(
    val id: Int?,
    val name: String,
    val birthday: LocalDate?,
    val music: String?,
    val imageUri: String?,
)
