package org.sopt.dosopttemplate.domain.entity

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null,
)
