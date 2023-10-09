package org.sopt.dosopttemplate.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.data.entity.User
import org.sopt.dosopttemplate.databinding.ActivityLoginBinding
import org.sopt.dosopttemplate.presentation.main.MainActivity
import org.sopt.dosopttemplate.presentation.signup.SignupActivity
import org.sopt.dosopttemplate.util.binding.BindingActivity
import org.sopt.dosopttemplate.util.context.toast
import org.sopt.dosopttemplate.util.intent.getParcelable
import org.sopt.dosopttemplate.util.view.snackBar

class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initResultLauncher()
        initSignupButtonClickListener()
        initLoginButtonClickListener()
    }

    private fun initResultLauncher() {
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val user = result.data?.getParcelable(USER_KEY, User::class.java)

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
            binding.root.snackBar { getString(R.string.login_fail_login) }
        }
    }

    private fun setLoginButtonClickListener(user: User?) {
        binding.btnLoginLogin.setOnClickListener {
            if (isValidLogin(user)) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(USER_KEY, user)
                this.toast(getString(R.string.login_success_login))
                startActivity(intent)
            }
        }
    }

    private fun isValidLogin(user: User?): Boolean =
        binding.etLoginId.text.toString() == user?.id && binding.etLoginPw.text.toString() == user.pw

    companion object {
        private const val USER_KEY = "user"
    }
}