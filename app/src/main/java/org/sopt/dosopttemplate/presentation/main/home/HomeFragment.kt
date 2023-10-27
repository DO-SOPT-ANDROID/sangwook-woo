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
import org.sopt.dosopttemplate.util.view.UiState
import org.sopt.dosopttemplate.util.view.snackBar

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel by viewModels<HomeViewModel>()
    lateinit var myInfo: HomeModel.MyInfoModel
    lateinit var homeAdapter: HomeAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initHomeAdapter()
        initFriendListStateObserver()
        getMyInfo()
        getFriendList()
    }

    private fun initHomeAdapter() {
        runCatching { homeAdapter = HomeAdapter()
            binding.rvHome.adapter = homeAdapter }.onFailure { t->
            if(t is IllegalArgumentException){
                t.printStackTrace()
            }
        }
    }

    private fun initFriendListStateObserver() {
        viewModel.friendListState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    submitHomeList(state)
                }

                is UiState.Failure -> {
                    binding.root.snackBar { state.msg }
                }
                else -> {}
            }
        }
    }

    private fun submitHomeList(state: UiState.Success<List<HomeModel.FriendInfoModel>>) {
        val list = listOf(myInfo) + state.data.sortedBy { it.name }
        homeAdapter.submitList(list)
    }

    private fun getMyInfo() {
        val userModel = requireActivity().intent.getParcelable(USER_KEY, UserModel::class.java)
        val myInformation = HomeModel.MyInfoModel(0, userModel?.nickname, userModel?.discription)
        myInfo = myInformation
    }

    private fun getFriendList() {
        viewModel.getFriendList()
    }

    companion object {
        private const val USER_KEY = "user"
    }
}