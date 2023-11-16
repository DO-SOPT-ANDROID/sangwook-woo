package org.sopt.dosopttemplate.presentation.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.data.ServicePool
import org.sopt.dosopttemplate.data.model.remote.request.RequestLoginDto
import org.sopt.dosopttemplate.data.model.remote.response.ResponseLoginDto
import org.sopt.dosopttemplate.data.service.AuthService
import org.sopt.dosopttemplate.domain.repository.SharedPrefRepository
import org.sopt.dosopttemplate.presentation.model.UserModel
import org.sopt.dosopttemplate.util.view.UiState
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sharedPrefRepository: SharedPrefRepository
) : ViewModel() {
    private val _loginState = MutableLiveData<UiState<UserModel?>>(UiState.Empty)
    val loginState: LiveData<UiState<UserModel?>> get() = _loginState

    val id = MutableLiveData<String>()
    val pw = MutableLiveData<String>()

    init {
        autoLogin()
    }

    private fun isValidLogin(userModel: UserModel? = UserModel().toUserModel(sharedPrefRepository.getUserInfo())): Boolean =
        id.value == userModel?.id && pw.value == userModel?.pw

    /*fun login(userModel: UserModel? = null) {
        when (if (userModel == null) isValidLogin() else isValidLogin(userModel)) {
            true -> _loginState.value =
                UiState.Success(UserModel().toUserModel(sharedPrefRepository.getUserInfo()))

            false -> _loginState.value = UiState.Failure(CODE_FAILURE)
        }
    }*/

    fun login() {
        val user = RequestLoginDto(
            userName = id.value,
            password = pw.value
        )

        viewModelScope.launch {
            runCatching { ServicePool.authService.login(user) }
                .onSuccess { response ->
                    _loginState.value = UiState.Success(UserModel(
                        id = response.userName , nickname = response.nickname
                    ))
                }
                .onFailure{
                    //실패 처리
                }
        }
    }

    private fun autoLogin() {
        if (sharedPrefRepository.isAutoLogin()) _loginState.value =
            UiState.Success(UserModel().toUserModel(sharedPrefRepository.getUserInfo()))
    }

    fun setAutoLogin(userModel: UserModel?) {
        sharedPrefRepository.saveUserInfo(userModel?.toUser())
        sharedPrefRepository.setAutoLogin()
    }

    companion object {
        const val CODE_FAILURE = "fail"
    }
}