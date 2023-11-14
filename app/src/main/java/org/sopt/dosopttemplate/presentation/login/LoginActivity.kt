package org.sopt.dosopttemplate.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.ActivityLoginBinding
import org.sopt.dosopttemplate.presentation.main.MainActivity
import org.sopt.dosopttemplate.presentation.model.UserModel
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
        initLoginStateObserver()
        initHideKeyboard()
    }

    private fun initResultLauncher() {
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val userModel = result.data?.getParcelable(USER_KEY, UserModel::class.java)
                viewModel.setAutoLogin(userModel)
                binding.root.snackBar { getString(R.string.login_success_signup) }
                setLoginButtonClickListener(userModel)
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

    private fun setLoginButtonClickListener(userModel: UserModel?) {
        binding.btnLoginLogin.setOnClickListener {
            viewModel.login(userModel)
        }
    }

    private fun initLoginStateObserver() {
        viewModel.loginState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Success -> {
                    navigateToMainScreenWithUserData(state.data)
                }

                is UiState.Empty -> {}
                else -> binding.root.snackBar { getString(R.string.login_fail_login) }
            }
        }.launchIn(lifecycleScope)
    }

    private fun navigateToMainScreenWithUserData(userModel: UserModel?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(USER_KEY, userModel)
        this.toast(getString(R.string.login_success_login))
        viewModel.setAutoLogin(userModel)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun initHideKeyboard() {
        binding.root.setOnClickListener { hideKeyboard() }
    }

    companion object {
        private const val USER_KEY = "user"
    }
}