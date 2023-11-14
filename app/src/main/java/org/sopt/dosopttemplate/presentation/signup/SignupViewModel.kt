package org.sopt.dosopttemplate.presentation.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.sopt.dosopttemplate.presentation.model.UserModel
import org.sopt.dosopttemplate.util.view.UiState

class SignupViewModel : ViewModel() {
    val id = MutableLiveData<String>()
    val pw = MutableLiveData<String>()
    val nickname = MutableLiveData<String>()
    val hobby = MutableLiveData<String>()

    private val _signupState = MutableStateFlow<UiState<UserModel>>(UiState.Empty)
    val signupState: StateFlow<UiState<UserModel>> get() = _signupState

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

    fun signUp(userModel: UserModel) {
        when {
            isValidSignup() -> _signupState.value = UiState.Success(userModel)
            !isValidId(id.value) -> _signupState.value = UiState.Failure(CODE_INVALID_ID)
            !isValidPw(pw.value) -> _signupState.value = UiState.Failure(CODE_INVALID_PW)
            !isValidNickName(nickname.value) -> _signupState.value =
                UiState.Failure(CODE_INVALID_NICKNAME)

            !isValidHobby(hobby.value) -> _signupState.value =
                UiState.Failure(CODE_INVALID_NICKNAME)
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