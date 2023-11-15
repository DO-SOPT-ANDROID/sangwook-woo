package org.sopt.dosopttemplate.presentation.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.domain.entity.ReqresUser
import org.sopt.dosopttemplate.domain.repository.UserRepository
import org.sopt.dosopttemplate.util.view.UiState
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _friendDeleteState = MutableStateFlow<UiState<Any>>(UiState.Empty)
    val friendDeleteState: StateFlow<UiState<Any>> = _friendDeleteState.asStateFlow()

    private val _userListState =
        MutableStateFlow<UiState<PagingData<ReqresUser>>>(UiState.Empty)
    val userListState: StateFlow<UiState<PagingData<ReqresUser>>> = _userListState.asStateFlow()

    init {
        getUsers()
    }

    fun getUsers() {
        viewModelScope.launch {
            _userListState.emit(UiState.Loading)
            userRepository.getUser()
                .cachedIn(viewModelScope)
                .catch { t ->
                    if (t is HttpException) {
                        Log.e("서버통신", "Http오류 ! ${t.message}, ${t.code()}")
                    }
                }
                .collectLatest { data ->
                    _userListState.emit(UiState.Success(data))
                }
        }
    }

    companion object {
        private const val ID_NULL_ERROR = "id is null"
    }
}