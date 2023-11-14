package org.sopt.dosopttemplate.data.datasource.local

import org.sopt.dosopttemplate.data.model.local.UserDto

interface SharedPrefDataSource {
    fun saveUserInfo(userDto: UserDto?)
    fun getUserInfo(): UserDto
    fun setAutoLogin()
    fun isAutoLogin(): Boolean
    fun clearAutoLogin()
    fun clearSharedPref()
}