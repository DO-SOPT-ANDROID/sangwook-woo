package org.sopt.dosopttemplate.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.ActivityMainBinding
import org.sopt.dosopttemplate.presentation.login.LoginActivity
import org.sopt.dosopttemplate.presentation.main.doandroid.DoAndroidFragment
import org.sopt.dosopttemplate.presentation.main.home.HomeFragment
import org.sopt.dosopttemplate.presentation.main.mypage.MyPageFragment
import org.sopt.dosopttemplate.util.binding.BindingActivity
import org.sopt.dosopttemplate.util.context.toast
import org.sopt.dosopttemplate.util.view.UiState
import org.sopt.dosopttemplate.util.view.snackBar

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    val viewModel by viewModels<MainViewModel>()
    var backPressedTime = 0L
    val callback = object : OnBackPressedCallback(true) {
        //뒤로가기 버튼 콜백
        override fun handleOnBackPressed() {
            if (System.currentTimeMillis() - backPressedTime >= MIN_TOUCH_DURATION) {//버튼 클릭 딜레이 1500 이상일때
                backPressedTime = System.currentTimeMillis()
                binding.root.snackBar { getString(R.string.main_onbackpressed) }
            } else {//미만일때
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAddCallback()
        initFragment()
        initBnvItemSelectedListener()
        initLogoutStateObserver()
    }

    private fun initAddCallback() {
        this.onBackPressedDispatcher.addCallback(this, callback)//콜백 등록
    }

    private fun initFragment() {
        navigateTo<HomeFragment>()
        binding.bnvMain.selectedItemId = R.id.menu_home
    }

    private fun initLogoutStateObserver() {
        viewModel.logoutState.observe(this) { state ->
            when (state) {
                is UiState.Success -> {
                    when (state.data) {
                        MainViewModel.CODE_LOGOUT -> this.toast(getString(R.string.main_success_logout))
                        MainViewModel.CODE_WITHDRAW -> this.toast(getString(R.string.main_success_withdraw))
                    }
                    navigateToLoginScreen()
                }

                else -> {}
            }
        }
    }
    private fun navigateToLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
    private fun initBnvItemSelectedListener() {
        binding.bnvMain.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.menu_do_android -> navigateTo<DoAndroidFragment>()
                R.id.menu_home -> navigateTo<HomeFragment>()
                R.id.menu_my_page -> navigateTo<MyPageFragment>()
            }
            true
        }
    }

    private inline fun <reified T : Fragment> navigateTo() {
        supportFragmentManager.commit {
            replace<T>(R.id.fcv_main, T::class.simpleName)
        }
    }

    companion object {
        private const val MIN_TOUCH_DURATION = 1500
    }
}