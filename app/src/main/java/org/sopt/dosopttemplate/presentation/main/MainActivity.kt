package org.sopt.dosopttemplate.presentation.main

import android.os.Bundle
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.data.entity.User
import org.sopt.dosopttemplate.databinding.ActivityMainBinding
import org.sopt.dosopttemplate.util.binding.BindingActivity
import org.sopt.dosopttemplate.util.intent.getParcelable

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getUserInfo()
    }

    private fun getUserInfo() {
        val intent = intent
        val user = intent.getParcelable(USER_KEY, User::class.java)

        binding.tvMainNickname.text = user?.nickname
        binding.tvMainId.text = user?.id
        binding.tvMainHobby.text = user?.hobby
    }

    companion object {
        private const val USER_KEY = "user"
    }
}