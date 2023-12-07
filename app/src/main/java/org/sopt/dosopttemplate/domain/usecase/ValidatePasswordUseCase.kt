package org.sopt.dosopttemplate.domain.usecase

import org.sopt.dosopttemplate.domain.entity.ValidationResult
import org.sopt.dosopttemplate.domain.validator.Validator

class ValidatePasswordUseCase(
    private val validator: Validator
) {
    operator fun invoke(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(
                successful = false
            )
        }
        return validator.isValidPassword(password)
    }
}