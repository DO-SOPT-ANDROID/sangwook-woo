package org.sopt.dosopttemplate.presentation.signup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.domain.repository.AuthRepository
import org.sopt.dosopttemplate.presentation.model.UserModel
import org.sopt.dosopttemplate.util.view.UiState
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    val id = MutableLiveData<String>()
    val pw = MutableLiveData<String>()
    val nickname = MutableLiveData<String>()
    val hobby = MutableLiveData<String>()

    private val _signupState = MutableStateFlow<UiState<Any>>(UiState.Empty)
    val signupState: StateFlow<UiState<Any>> = _signupState.asStateFlow()

    private fun isValidSignup(): Boolean =
        isValidId(id.value) && isValidPw(pw.value) && isValidNickName(nickname.value) && isValidHobby(
            hobby.value
        )

    private fun isValidId(id: String?): Boolean =
        id?.length in MIN_ID_LENGTH..MAX_ID_LENGTH

    private fun isValidPw(pw: String?): Boolean =
        pw?.length in MIN_PW_LENGTH..MAX_PW_LENGTH

    private fun isValidNickName(nickname: String?): Boolean =
        nickname?.isNotBlank() ?: false

    private fun isValidHobby(hobby: String?): Boolean =
        hobby?.isNotBlank() ?: false

    fun signUp() {
        val userModel = UserModel(
            id.value,
            pw.value,
            nickname.value,
            hobby.value
        )
        when {
            isValidSignup() -> {
                handleValidSignup(userModel)
            }
            !isValidId(id.value) -> _signupState.value = UiState.Failure(CODE_INVALID_ID)
            !isValidPw(pw.value) -> _signupState.value = UiState.Failure(CODE_INVALID_PW)
            !isValidNickName(nickname.value) -> _signupState.value =
                UiState.Failure(CODE_INVALID_NICKNAME)
            !isValidHobby(hobby.value) -> _signupState.value =
                UiState.Failure(CODE_INVALID_HOBBY)
        }
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

    companion object {
        private const val MIN_ID_LENGTH = 6
        private const val MAX_ID_LENGTH = 10
        private const val MIN_PW_LENGTH = 8
        private const val MAX_PW_LENGTH = 12
        private const val CODE_INVALID_ID = "idFail"
        private const val CODE_INVALID_PW = "pwFail"
        private const val CODE_INVALID_NICKNAME = "nicknameFail"
        private const val CODE_INVALID_HOBBY = "hobbyFail"
    }
}