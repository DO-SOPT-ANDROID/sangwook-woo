package org.sopt.dosopttemplate.domain.usecase

import org.sopt.dosopttemplate.domain.entity.ValidationResult
import org.sopt.dosopttemplate.domain.validator.Validator

class ValidateIdUseCase(
    private val validator: Validator
) {
    operator fun invoke(id: String): ValidationResult {
        if (id.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "아이디를 입력해주세요"
            )
        }
        return validator.isValidId(id)
    }
}