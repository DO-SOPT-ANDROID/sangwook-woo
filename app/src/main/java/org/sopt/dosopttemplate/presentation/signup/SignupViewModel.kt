package org.sopt.dosopttemplate.presentation.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.domain.entity.ValidationResult
import org.sopt.dosopttemplate.domain.repository.AuthRepository
import org.sopt.dosopttemplate.domain.usecase.ValidateIdUseCase
import org.sopt.dosopttemplate.domain.usecase.ValidatePasswordUseCase
import org.sopt.dosopttemplate.presentation.model.UserModel
import org.sopt.dosopttemplate.util.view.UiState
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val validateId: ValidateIdUseCase,
    private val validatePassword: ValidatePasswordUseCase
) : ViewModel() {
    val id = MutableStateFlow("")
    val pw = MutableStateFlow("")
    val nickname = MutableStateFlow("")
    val hobby = MutableStateFlow("")

    val idFocus = MutableStateFlow(false)
    val pwFocus = MutableStateFlow(false)

    val idValidation: StateFlow<ValidationResult> = id.map { id -> validateId(id) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ValidationResult(false))
    val pwValidation: StateFlow<ValidationResult> = pw.map { pw -> validatePassword(pw) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ValidationResult(false))
    private val nicknameValidation: StateFlow<Boolean> =
        nickname.map { nickname -> nickname.isNotBlank() }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val signupValidation: StateFlow<Boolean> = combine(
        idValidation,
        pwValidation,
        nicknameValidation
    ) { idResult, pwResult, nicknameResult ->
        idResult.successful && pwResult.successful && nicknameResult
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    private val _signupState = MutableStateFlow<UiState<Any>>(UiState.Empty)
    val signupState: StateFlow<UiState<Any>> = _signupState.asStateFlow()

    fun signUp() {
        val userModel = UserModel(
            id.value,
            pw.value,
            nickname.value,
            hobby.value
        )
        handleValidSignup(userModel)
    }

    private fun handleValidSignup(userModel: UserModel) {
        viewModelScope.launch {
            authRepository.signup(userModel.toUser())
                .onSuccess { response ->
                    _signupState.value = UiState.Success(userModel)
                    Log.e("서버통신", "회원가입 성공 ${Response.success(response).headers()}")
                }
                .onFailure { t ->
                    if (t is HttpException) {
                        Log.e("서버통신", "HTTP 실패${t.code()}")
                    }
                    Log.e("서버통신", "${t.message}")
                    _signupState.value = UiState.Failure("${t.message}")
                }
        }
    }
}