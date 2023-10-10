package org.sopt.dosopttemplate.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.data.entity.User
import org.sopt.dosopttemplate.databinding.ActivityLoginBinding
import org.sopt.dosopttemplate.presentation.main.MainActivity
import org.sopt.dosopttemplate.presentation.signup.SignupActivity
import org.sopt.dosopttemplate.util.activity.hideKeyboard
import org.sopt.dosopttemplate.util.binding.BindingActivity
import org.sopt.dosopttemplate.util.context.toast
import org.sopt.dosopttemplate.util.intent.getParcelable
import org.sopt.dosopttemplate.util.view.UiState
import org.sopt.dosopttemplate.util.view.snackBar

@AndroidEntryPoint
class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val viewModel by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initResultLauncher()
        initSignupButtonClickListener()
        initLoginButtonClickListener()
        initloginStateObserver()
        initHideKeyboard()
    }

    private fun initResultLauncher() {
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val user = result.data?.getParcelable(USER_KEY, User::class.java)
                viewModel.setAutoLogin(user)
                binding.root.snackBar { getString(R.string.login_success_signup) }
                setLoginButtonClickListener(user)
            }
        }
    }

    private fun initSignupButtonClickListener() {
        binding.btnLoginSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

    private fun initLoginButtonClickListener() {
        binding.btnLoginLogin.setOnClickListener {
            viewModel.login()
        }
    }

    private fun setLoginButtonClickListener(user: User?) {
        binding.btnLoginLogin.setOnClickListener {
            viewModel.login(user)
        }
    }

    private fun initloginStateObserver() {
        viewModel.loginState.observe(this) { state ->
            when (state) {
                is UiState.Success -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra(USER_KEY, state.data)
                    this.toast(getString(R.string.login_success_login))
                    startActivity(intent)
                    finish()
                }

                is UiState.Empty -> {}
                else -> binding.root.snackBar { getString(R.string.login_fail_login) }
            }
        }
    }

    private fun initHideKeyboard() {
        binding.root.setOnClickListener { hideKeyboard() }
    }

    companion object {
        private const val USER_KEY = "user"
    }
}