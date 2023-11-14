package org.sopt.dosopttemplate.data.datasourceimpl.local

import android.content.SharedPreferences
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.sopt.dosopttemplate.data.datasource.local.SharedPrefDataSource
import org.sopt.dosopttemplate.data.model.local.UserDto
import javax.inject.Inject

class SharedPrefDataSourceImpl @Inject constructor(
    private val sharedPref: SharedPreferences
) : SharedPrefDataSource {
    override fun saveUserInfo(userDto: UserDto?) {
        val json = Json.encodeToString(userDto)
        sharedPref.edit().putString("user", json).apply()
    }

    override fun getUserInfo(): UserDto {
        val json = sharedPref.getString("user", "")
        if (json.isNullOrBlank()) return UserDto("", "", "", "")
        return Json.decodeFromString(json)
    }

    override fun setAutoLogin() {
        sharedPref.edit().putBoolean("autoLogin", true).apply()
    }

    override fun isAutoLogin(): Boolean {
        return sharedPref.getBoolean("autoLogin", false)
    }

    override fun clearAutoLogin() {
        sharedPref.edit().putBoolean("autoLogin", false).apply()
    }

    override fun clearSharedPref() {
        sharedPref.edit().clear().apply()
    }

}