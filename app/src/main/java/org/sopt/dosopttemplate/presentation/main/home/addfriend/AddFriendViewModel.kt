package org.sopt.dosopttemplate.presentation.main.home.addfriend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.domain.repository.FriendLocalRepository
import org.sopt.dosopttemplate.presentation.model.HomeModel
import org.sopt.dosopttemplate.util.view.UiState
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddFriendViewModel @Inject constructor(
    private val friendLocalRepository: FriendLocalRepository
) : ViewModel() {
    private val _addFriendState = MutableLiveData<UiState<HomeModel.FriendInfoModel>>(UiState.Empty)
    val addFriendState: LiveData<UiState<HomeModel.FriendInfoModel>> get() = _addFriendState
    val name = MutableLiveData<String>()
    val music = MutableLiveData<String>()
    val birthday = MutableLiveData<String>()

    fun addFriend() {
        if (name.value.isNullOrBlank()) {
            _addFriendState.value = UiState.Failure(NAME_ERROR)
            return
        }
        if (birthday.value.isNullOrBlank()) {
            _addFriendState.value = UiState.Failure(BIRTHDAY_ERROR)
            return
        }
        val friend = HomeModel.FriendInfoModel(
            null,
            name.value.toString(),
            LocalDate.parse(birthday.value),
            music.value,
            null
        )
        viewModelScope.launch {
            friendLocalRepository.addFriend(
                HomeModel.FriendInfoModel.toFriend(
                    listOf(friend)
                ).first()
            )
        }
        _addFriendState.value = UiState.Success(friend)
    }

    companion object {
        private const val NAME_ERROR = "name error"
        private const val BIRTHDAY_ERROR = "birthday error"
    }
}