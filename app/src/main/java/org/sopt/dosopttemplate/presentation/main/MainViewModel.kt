package org.sopt.dosopttemplate.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.sopt.dosopttemplate.domain.repository.SharedPrefRepository
import org.sopt.dosopttemplate.util.view.UiState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val sharedPrefRepository: SharedPrefRepository
) : ViewModel() {
    private val _logoutState = MutableLiveData<UiState<String?>>(UiState.Empty)
    val logoutState: LiveData<UiState<String?>> get() = _logoutState

    fun logout() {
        sharedPrefRepository.clearAutoLogin()
        _logoutState.value = UiState.Success(CODE_LOGOUT)
    }

    fun withdraw() {
        sharedPrefRepository.clearSharedPref()
        _logoutState.value = UiState.Success(CODE_WITHDRAW)
    }

    companion object {
        const val CODE_LOGOUT = "logout"
        const val CODE_WITHDRAW = "withdraw"
    }
}