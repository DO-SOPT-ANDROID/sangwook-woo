package org.sopt.dosopttemplate.data.validatorimpl

import org.sopt.dosopttemplate.domain.entity.ValidationResult
import org.sopt.dosopttemplate.domain.validator.Validator
import java.util.regex.Pattern
import javax.inject.Inject

class ValidatorImpl @Inject constructor() : Validator {
    override fun isValidId(id: String): ValidationResult {
        if(idPattern.matcher(id).matches()){
            return ValidationResult(
                successful = true,
                errorMessage = null
            )
        }
        return ValidationResult(
            successful = false,
            errorMessage = "아이디는 영문, 숫자가 포함되어야 하고 6~10글자"
        )
    }

    override fun isValidPassword(password: String): ValidationResult {
        if(passwordPattern.matcher(password).matches()){
            return ValidationResult(
                successful = true,
                errorMessage = null
            )
        }
        return ValidationResult(
            successful = false,
            errorMessage = "비밀번호는 영문, 숫자, 특수문자가 포함되어야 하고 6~12글자"
        )
    }

    companion object {
        private const val ID_PATTERN = "^(?=.*[A-za-z])(?=.*\\d)[a-zA-Z0-9]{6,10}$"
        private const val PASSWORD_PATTERN =
            "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{6,12}$"

        private val idPattern = Pattern.compile(ID_PATTERN)
        private val passwordPattern = Pattern.compile(PASSWORD_PATTERN)
    }
}