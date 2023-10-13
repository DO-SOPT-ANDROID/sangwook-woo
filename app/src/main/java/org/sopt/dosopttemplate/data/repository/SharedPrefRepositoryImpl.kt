package org.sopt.dosopttemplate.data.repository

import org.sopt.dosopttemplate.data.model.UserDto
import org.sopt.dosopttemplate.data.local.SharedPrefDataSource
import org.sopt.dosopttemplate.domain.entity.User
import org.sopt.dosopttemplate.domain.repository.SharedPrefRepository
import javax.inject.Inject

class SharedPrefRepositoryImpl @Inject constructor(
    private val sharedPrefDataSource: SharedPrefDataSource
) : SharedPrefRepository {
    override fun saveUserInfo(user: User?) {
        sharedPrefDataSource.saveUserInfo(UserDto().toUserDto(user))
    }

    override fun getUserInfo(): User? {
        return sharedPrefDataSource.getUserInfo().toUser()
    }

    override fun setAutoLogin() {
        sharedPrefDataSource.setAutoLogin()
    }

    override fun isAutoLogin(): Boolean {
        return sharedPrefDataSource.isAutoLogin()
    }

    override fun clearAutoLogin() {
        sharedPrefDataSource.clearAutoLogin()
    }

    override fun clearSharedPref() {
        sharedPrefDataSource.clearSharedPref()
    }
}