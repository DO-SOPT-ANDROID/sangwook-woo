package org.sopt.dosopttemplate.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.data.entity.User
import org.sopt.dosopttemplate.databinding.ActivityMainBinding
import org.sopt.dosopttemplate.presentation.login.LoginActivity
import org.sopt.dosopttemplate.presentation.main.MainViewModel.Companion.CODE_LOGOUT
import org.sopt.dosopttemplate.presentation.main.MainViewModel.Companion.CODE_WITHDRAW
import org.sopt.dosopttemplate.util.binding.BindingActivity
import org.sopt.dosopttemplate.util.context.toast
import org.sopt.dosopttemplate.util.intent.getParcelable
import org.sopt.dosopttemplate.util.view.UiState
import org.sopt.dosopttemplate.util.view.snackBar

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    val viewModel by viewModels<MainViewModel>()
    var backPressedTime = 0L
    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (System.currentTimeMillis() - backPressedTime >= 1500) {
                backPressedTime = System.currentTimeMillis()
                binding.root.snackBar { getString(R.string.main_onbackpressed) }
            } else {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getUserInfo()
        initLogoutButtonClickListener()
        initWithdrawButtonClickListener()
        initLogoutStateObserver()
        initAddCallback()
    }

    private fun initAddCallback() {
        this.onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun getUserInfo() {
        val intent = intent
        val user = intent.getParcelable(USER_KEY, User::class.java)

        binding.data = user
    }

    private fun initLogoutButtonClickListener() {
        binding.btnMainLogout.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun initWithdrawButtonClickListener() {
        binding.btnMainWithdraw.setOnClickListener {
            viewModel.withdraw()
        }
    }

    private fun initLogoutStateObserver() {
        viewModel.logoutState.observe(this) { state ->
            when (state) {
                is UiState.Success -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    when (state.data) {
                        CODE_LOGOUT -> this.toast(getString(R.string.main_success_logout))
                        CODE_WITHDRAW -> this.toast(getString(R.string.main_success_withdraw))
                    }
                    startActivity(intent)
                    finish()
                }

                is UiState.Failure -> {}
                else -> {}
            }
        }
    }

    companion object {
        private const val USER_KEY = "user"
    }
}