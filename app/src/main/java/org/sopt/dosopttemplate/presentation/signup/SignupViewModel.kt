package org.sopt.dosopttemplate.presentation.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val ValidId : ValidateIdUseCase,
    private val ValidPassword : ValidatePasswordUseCase
) : ViewModel() {
    val id = MutableLiveData("")
    val pw = MutableLiveData("")
    val nickname = MutableLiveData<String>()
    val hobby = MutableLiveData<String>()

    val idFocus = MutableLiveData(false)
    val pwFocus = MutableLiveData(false)

    val idValidation : LiveData<ValidationResult> = id.map { id -> ValidId(id) }
    val pwValidation : LiveData<ValidationResult> = pw.map { pw -> ValidPassword(pw) }
    private val nicknameValidation : LiveData<Boolean> = nickname.map { nickname -> nickname.isNotBlank()}
    val signupValidation: MediatorLiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(idValidation) { idResult ->
            value = combineResults(idResult, pwValidation.value, nicknameValidation.value)
        }

        addSource(pwValidation) { pwResult ->
            value = combineResults(idValidation.value, pwResult, nicknameValidation.value)
        }

        addSource(nicknameValidation) { nicknameResult ->
            value = combineResults(idValidation.value, pwValidation.value, nicknameResult)
        }
    }

    private fun combineResults(idResult: ValidationResult?, pwResult: ValidationResult?, nicknameResult: Boolean?): Boolean {
        return idResult?.successful == true && pwResult?.successful == true && nicknameResult == true
    }

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

    fun handleValidSignup(userModel: UserModel) {
        viewModelScope.launch {
            authRepository.signup(userModel.toUser())
                .onSuccess { response ->
                    _signupState.value = UiState.Success(userModel)
                    Log.e("서버통신","회원가입 성공 ${Response.success(response).headers()}")
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