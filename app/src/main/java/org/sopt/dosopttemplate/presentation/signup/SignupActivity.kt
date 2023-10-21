package org.sopt.dosopttemplate.presentation.signup

import android.os.Bundle
import androidx.activity.viewModels
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.ActivitySignupBinding
import org.sopt.dosopttemplate.presentation.model.UserModel
import org.sopt.dosopttemplate.util.activity.hideKeyboard
import org.sopt.dosopttemplate.util.binding.BindingActivity
import org.sopt.dosopttemplate.util.view.UiState
import org.sopt.dosopttemplate.util.view.snackBar

class SignupActivity : BindingActivity<ActivitySignupBinding>(R.layout.activity_signup) {
    private val viewModel by viewModels<SignupViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initSignupButtonClickListener()
        initSignupStateObserver()
        initHideKeyboard()
    }

    private fun initSignupButtonClickListener() {
        binding.btnSignupSignup.setOnClickListener {
            val userModel = UserModel(
                binding.etSignupId.text.toString(),
                binding.etSignupPw.text.toString(),
                binding.etSignupNickname.text.toString(),
                binding.etSignupHobby.text.toString()
            )
            viewModel.signUp(userModel)
        }
    }

    private fun initSignupStateObserver() {
        viewModel.signupState.observe(this) { state ->
            when (state) {
                is UiState.Success -> {
                    intent.putExtra(USER_KEY, state.data)
                    setResult(RESULT_OK, intent)
                    finish()
                }

                is UiState.Failure -> {
                    handleFailureState(state.msg)
                }

                else -> {}
            }
        }
    }

    private fun handleFailureState(msg: String) {
        when (msg) {
            CODE_INVALID_ID -> {
                binding.root.snackBar { getString(R.string.signup_fail_id) }
            }

            CODE_INVALID_PW -> {
                binding.root.snackBar { getString(R.string.signup_fail_pw) }
            }

            CODE_INVALID_NICKNAME -> {
                binding.root.snackBar { getString(R.string.signup_fail_nickname) }
            }

            CODE_INVALID_HOBBY -> {
                binding.root.snackBar { getString(R.string.signup_fail_hobby) }
            }
        }
    }

    private fun initHideKeyboard() {
        binding.root.setOnClickListener { hideKeyboard() }
        binding.clSignup.setOnClickListener { hideKeyboard() }
    }

    companion object {
        private const val USER_KEY = "user"
        private const val CODE_INVALID_ID = "idFail"
        private const val CODE_INVALID_PW = "pwFail"
        private const val CODE_INVALID_NICKNAME = "nicknameFail"
        private const val CODE_INVALID_HOBBY = "hobbyFail"
    }
}