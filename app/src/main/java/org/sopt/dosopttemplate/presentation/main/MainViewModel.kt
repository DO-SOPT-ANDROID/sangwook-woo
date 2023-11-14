package org.sopt.dosopttemplate.presentation.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.sopt.dosopttemplate.domain.repository.SharedPrefRepository
import org.sopt.dosopttemplate.util.view.UiState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPrefRepository: SharedPrefRepository
) : ViewModel() {
    private val _logoutState = MutableStateFlow<UiState<String?>>(UiState.Empty)
    val logoutState: StateFlow<UiState<String?>> get() = _logoutState

    fun logout() {
        runCatching {
            sharedPrefRepository.clearAutoLogin()
        }.onSuccess {
            _logoutState.value = UiState.Success(CODE_LOGOUT)
        }.onFailure { t ->
            _logoutState.value = UiState.Failure("${t.message}")
        }
    }

    fun withdraw() {
        runCatching {
            sharedPrefRepository.clearSharedPref()
        }.onSuccess {
            _logoutState.value = UiState.Success(CODE_WITHDRAW)
        }.onFailure { t ->
            _logoutState.value = UiState.Failure("${t.message}")
        }
    }

    companion object {
        const val CODE_LOGOUT = "logout"
        const val CODE_WITHDRAW = "withdraw"
    }
}