package org.sopt.dosopttemplate.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.sopt.dosopttemplate.data.entity.User
import org.sopt.dosopttemplate.domain.repository.SharedPrefRepository
import org.sopt.dosopttemplate.util.view.UiState
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val sharedPrefRepository: SharedPrefRepository
) : ViewModel() {
    private val _loginState = MutableLiveData<UiState<User?>>(UiState.Empty)
    val loginState: LiveData<UiState<User?>> get() = _loginState

    val id = MutableLiveData<String>()
    val pw = MutableLiveData<String>()

    init {
        autoLogin()
    }

    private fun isValidLogin(user: User? = sharedPrefRepository.getUserInfo()): Boolean =
        id.value == user?.id && pw.value == user?.pw

    fun login(user: User? = null) {
        when (if (user == null) isValidLogin() else isValidLogin(user)) {
            true -> _loginState.value = UiState.Success(sharedPrefRepository.getUserInfo())
            false -> _loginState.value = UiState.Failure(CODE_FAILURE)
        }
    }

    private fun autoLogin() {
        if (sharedPrefRepository.isAutoLogin()) _loginState.value =
            UiState.Success(sharedPrefRepository.getUserInfo())
    }

    fun setAutoLogin(user: User?) {
        sharedPrefRepository.saveUserInfo(user)
        sharedPrefRepository.setAutoLogin()
    }

    companion object {
        const val CODE_FAILURE = "fail"
    }
}