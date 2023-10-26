package org.sopt.dosopttemplate.presentation.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.FragmentHomeBinding
import org.sopt.dosopttemplate.presentation.model.HomeModel
import org.sopt.dosopttemplate.presentation.model.UserModel
import org.sopt.dosopttemplate.util.binding.BindingFragment
import org.sopt.dosopttemplate.util.intent.getParcelable

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel by viewModels<HomeViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initHomeRecyclerViewAdapter()
    }

    private fun initHomeRecyclerViewAdapter() {
        val userModel = requireActivity().intent.getParcelable(USER_KEY, UserModel::class.java)
        val myInfo = HomeModel.MyInfoModel(userModel?.nickname, userModel?.discription)
        val adapter = HomeAdapter()
        val list = listOf(myInfo) + viewModel.friendInfoList.sortedBy { it.name }
        adapter.submitList(list)
        binding.rvHome.adapter = adapter
    }
    companion object {
        private const val USER_KEY = "user"
    }
}