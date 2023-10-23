package org.sopt.dosopttemplate.presentation.main.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.FragmentMypageBinding
import org.sopt.dosopttemplate.presentation.main.MainViewModel
import org.sopt.dosopttemplate.presentation.model.UserModel
import org.sopt.dosopttemplate.util.binding.BindingFragment
import org.sopt.dosopttemplate.util.intent.getParcelable

@AndroidEntryPoint
class MyPageFragment : BindingFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {
    private val viewModel by activityViewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUserInfo()
        initLogoutButtonClickListener()
        initWithdrawButtonClickListener()
    }

    private fun getUserInfo() {
        val intent = requireActivity().intent
        val userModel = intent.getParcelable(USER_KEY, UserModel::class.java)

        binding.data = userModel
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

    companion object {
        private const val USER_KEY = "user"
    }
}