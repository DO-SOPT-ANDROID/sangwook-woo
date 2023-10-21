package org.sopt.dosopttemplate.domain.repository

import org.sopt.dosopttemplate.domain.entity.User

interface SharedPrefRepository {
    fun saveUserInfo(user: User?)
    fun getUserInfo(): User?
    fun setAutoLogin()
    fun isAutoLogin(): Boolean
    fun clearAutoLogin()
    fun clearSharedPref()
}