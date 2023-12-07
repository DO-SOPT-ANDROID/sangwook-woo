package org.sopt.dosopttemplate.domain.validator

import org.sopt.dosopttemplate.domain.entity.ValidationResult

interface Validator {
    fun isValidId(id: String): ValidationResult
    fun isValidPassword(password: String): ValidationResult
}