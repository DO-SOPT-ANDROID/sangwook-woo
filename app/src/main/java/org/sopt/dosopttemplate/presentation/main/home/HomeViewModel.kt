package org.sopt.dosopttemplate.presentation.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.domain.repository.FriendLocalRepository
import org.sopt.dosopttemplate.presentation.model.HomeModel
import org.sopt.dosopttemplate.presentation.model.HomeModel.FriendInfoModel.Companion.toFriendInfoModel
import org.sopt.dosopttemplate.util.view.UiState
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val friendLocalRepository: FriendLocalRepository
) : ViewModel() {
    private val _friendListState =
        MutableLiveData<UiState<List<HomeModel.FriendInfoModel>>>(UiState.Empty)
    val friendListState: LiveData<UiState<List<HomeModel.FriendInfoModel>>> get() = _friendListState

    fun getFriendList() {
        viewModelScope.launch {
            friendLocalRepository.getAll()
                .onSuccess { response ->
                    _friendListState.value = UiState.Success(toFriendInfoModel(response))
                }
                .onFailure { t ->
                    _friendListState.value = UiState.Failure("${t.message}")
                }
        }
    }
}