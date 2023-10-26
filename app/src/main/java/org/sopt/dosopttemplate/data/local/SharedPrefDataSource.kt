package org.sopt.dosopttemplate.data.local

import org.sopt.dosopttemplate.data.model.UserDto

interface SharedPrefDataSource {
    fun saveUserInfo(userDto: UserDto?)
    fun getUserInfo(): UserDto
    fun setAutoLogin()
    fun isAutoLogin(): Boolean
    fun clearAutoLogin()
    fun clearSharedPref()
}