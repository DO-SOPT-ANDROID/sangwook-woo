package org.sopt.dosopttemplate.data.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.sopt.dosopttemplate.data.entity.User
import org.sopt.dosopttemplate.domain.repository.SharedPrefRepository
import javax.inject.Inject

class SharedPrefRepositoryImpl @Inject constructor(
    private val sharedPref: SharedPreferences
) : SharedPrefRepository {
    val gson: Gson = GsonBuilder().create()
    override fun saveUserInfo(user: User?) {
        val value = gson.toJson(user)
        sharedPref.edit().putString("user", value).apply()
    }

    override fun getUserInfo(): User? {
        val value = sharedPref.getString("user", "")
        if (value.isNullOrBlank()) return null
        return gson.fromJson(value, User::class.java)
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