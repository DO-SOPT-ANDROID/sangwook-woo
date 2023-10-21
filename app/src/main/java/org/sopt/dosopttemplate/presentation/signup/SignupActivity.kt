package org.sopt.dosopttemplate.presentation.signup

import android.os.Bundle
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.data.entity.User
import org.sopt.dosopttemplate.databinding.ActivitySignupBinding
import org.sopt.dosopttemplate.util.binding.BindingActivity
import org.sopt.dosopttemplate.util.view.snackBar

class SignupActivity : BindingActivity<ActivitySignupBinding>(R.layout.activity_signup) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSignupButtonClickListener()
    }

    private fun initSignupButtonClickListener() {
        binding.btnSignupSignup.setOnClickListener {
            val user = User(
                binding.etSignupId.text.toString(),
                binding.etSignupPw.text.toString(),
                binding.etSignupNickname.text.toString(),
                binding.etSignupHobby.text.toString()
            )

            checkValidSignup(user)
        }
    }

    private fun checkValidSignup(user: User) {
        if (!isValidId(user.id)) {
            binding.root.snackBar { getString(R.string.signup_fail_id) }
        } else if (!isValidPw(user.pw)) {
            binding.root.snackBar { getString(R.string.signup_fail_pw) }
        } else if (!isValidNickName(user.nickname)) {
            binding.root.snackBar { getString(R.string.signup_fail_nickname) }
        } else if (!isValidHobby(user.hobby)) {
            binding.root.snackBar { getString(R.string.signup_fail_hobby) }
        } else {
            intent.putExtra(USER_KEY, user)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun isValidId(id: String): Boolean =
        id.length in MIN_ID_LENGTH..MAX_ID_LENGTH

    private fun isValidPw(pw: String): Boolean =
        pw.length in MIN_PW_LENGTH..MAX_PW_LENGTH

    private fun isValidNickName(nickname: String): Boolean =
        nickname.isNotBlank()

    private fun isValidHobby(hobby: String): Boolean =
        hobby.isNotBlank()

    companion object {
        private const val MIN_ID_LENGTH = 6
        private const val MAX_ID_LENGTH = 10
        private const val MIN_PW_LENGTH = 8
        private const val MAX_PW_LENGTH = 12
        private const val USER_KEY = "user"
    }
}