package org.sopt.dosopttemplate.presentation.login

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
import org.sopt.dosopttemplate.domain.repository.SharedPrefRepository
import org.sopt.dosopttemplate.presentation.model.UserModel
import org.sopt.dosopttemplate.presentation.model.UserModel.Companion.toUserModel
import org.sopt.dosopttemplate.util.view.UiState
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sharedPrefRepository: SharedPrefRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _loginState = MutableStateFlow<UiState<UserModel?>>(UiState.Empty)
    val loginState: StateFlow<UiState<UserModel?>> = _loginState.asStateFlow()

    val id = MutableLiveData<String>()
    val pw = MutableLiveData<String>()

    init {
        autoLogin()
    }

    fun login(userModel: UserModel = UserModel(id.value, pw.value)) {
        viewModelScope.launch {
            authRepository.login( userModel.toUser() )
                .onSuccess { response ->
                    _loginState.value = UiState.Success(toUserModel(response))
                    Log.e("서버통신","로그인 성공")
                    setAutoLogin(userModel)
                }
                .onFailure { t ->
                    if (t is HttpException) {
                        Log.e("서버통신", "HTTP 실패")
                    }
                    Log.e("서버통신", "${t.message}")
                    _loginState.value = UiState.Failure("${t.message}")
                }
        }
    }

    private fun autoLogin() {
        if (sharedPrefRepository.isAutoLogin()) {
            login(toUserModel(sharedPrefRepository.getUserInfo()))
        }
    }

    fun setAutoLogin(userModel: UserModel?) {
        sharedPrefRepository.saveUserInfo(userModel?.toUser())
        sharedPrefRepository.setAutoLogin()
    }

    companion object {
        const val CODE_FAILURE = "fail"
    }
}