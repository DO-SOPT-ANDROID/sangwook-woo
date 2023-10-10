package org.sopt.dosopttemplate.presentation.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.dosopttemplate.data.entity.User
import org.sopt.dosopttemplate.util.view.UiState

class SignupViewModel : ViewModel() {
    val id = MutableLiveData<String>()
    val pw = MutableLiveData<String>()
    val nickname = MutableLiveData<String>()
    val hobby = MutableLiveData<String>()

    private val _signupState = MutableLiveData<UiState<User>>(UiState.Empty)
    val signupState: LiveData<UiState<User>> get() = _signupState

    private fun isValidId(id: String?): Boolean =
        id?.length in MIN_ID_LENGTH..MAX_ID_LENGTH

    private fun isValidPw(pw: String?): Boolean =
        pw?.length in MIN_PW_LENGTH..MAX_PW_LENGTH

    private fun isValidNickName(nickname: String?): Boolean =
        nickname?.isNotBlank() ?: false

    private fun isValidHobby(hobby: String?): Boolean =
        hobby?.isNotBlank() ?: false

    fun signUp(user: User) {
        if (isValidId(id.value) &&
            isValidPw(pw.value) &&
            isValidNickName(nickname.value) &&
            isValidHobby(hobby.value)
        ) {
            _signupState.value = UiState.Success(user)
        } else if (!isValidId(id.value)) {
            _signupState.value = UiState.Failure(CODE_INVALID_ID)
        } else if (!isValidPw(pw.value)) {
            _signupState.value = UiState.Failure(CODE_INVALID_PW)
        } else if (!isValidNickName(nickname.value)) {
            _signupState.value = UiState.Failure(CODE_INVALID_NICKNAME)
        } else {
            _signupState.value = UiState.Failure(CODE_INVALID_HOBBY)
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