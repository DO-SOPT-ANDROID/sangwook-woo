package org.sopt.dosopttemplate.presentation.main.home

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.FragmentHomeBinding
import org.sopt.dosopttemplate.domain.entity.ReqresUser
import org.sopt.dosopttemplate.presentation.main.home.addfriend.AddFriendActivity
import org.sopt.dosopttemplate.presentation.main.home.frienddetail.FriendDetailActivity
import org.sopt.dosopttemplate.presentation.model.HomeModel
import org.sopt.dosopttemplate.presentation.model.UserInfoModel
import org.sopt.dosopttemplate.presentation.model.UserModel
import org.sopt.dosopttemplate.util.binding.BindingFragment
import org.sopt.dosopttemplate.util.fragment.AlertDialogFragment
import org.sopt.dosopttemplate.util.fragment.viewLifeCycle
import org.sopt.dosopttemplate.util.fragment.viewLifeCycleScope
import org.sopt.dosopttemplate.util.intent.getParcelable
import org.sopt.dosopttemplate.util.view.UiState
import org.sopt.dosopttemplate.util.view.snackBar

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val viewModel by viewModels<HomeViewModel>()
    lateinit var myInfo: HomeModel.MyInfoModel
    lateinit var userPagingAdapter: HomeAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initHomeAdapter()
        initFloatingBtnClickListener()
        initFriendDeleteStateObserver()
        initResultLauncher()
        initUserListStateObserver()
        getMyInfo()
        getFriendList()
    }

    private fun initFriendDeleteStateObserver() {
        viewModel.friendDeleteState.flowWithLifecycle(viewLifeCycle).onEach { state ->
            when (state) {
                is UiState.Success -> {
                    getFriendList()
                }

                is UiState.Failure -> {
                    binding.root.snackBar { state.msg }
                }

                else -> {}
            }
        }.launchIn(viewLifeCycleScope)
    }

    private fun initFloatingBtnClickListener() {
        binding.btnHomeFloating.setOnClickListener {
            navigateToAddFriendScreen()
        }
    }

    private fun initHomeAdapter() {
        userPagingAdapter = HomeAdapter(
            onLongClicked = {
            },
            onClicked = { friend ->
                initHomeAdapterOnClicked(friend)
            }
        )
        binding.rvHome.adapter = userPagingAdapter
    }

    private fun initHomeAdapterOnClicked(friend: ReqresUser) {
        val intent = Intent(requireContext(), FriendDetailActivity::class.java)
        val user = UserInfoModel.toUserInfoModel(friend)
        intent.putExtra(USER_KEY,user)
        startActivity(intent)
    }

    private fun initResultLauncher() {
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                viewModel.getUsers()
            }
        }
    }

    private fun initUserListStateObserver() {
        viewModel.userListState.flowWithLifecycle(viewLifeCycle).onEach { state ->
            when (state) {
                is UiState.Success -> {
                    userPagingAdapter.submitData(state.data)
                }

                else -> {

                }
            }
        }.launchIn(viewLifeCycleScope)
    }

    fun scrollTop() {
        binding.rvHome.smoothScrollToPosition(0)
    }

    private fun getMyInfo() {
        val userModel = requireActivity().intent.getParcelable(USER_KEY, UserModel::class.java)
        val myInformation = HomeModel.MyInfoModel(0, userModel?.nickname, userModel?.discription)
        myInfo = myInformation
    }

    private fun getFriendList() {
        viewModel.getUsers()
    }

    private fun navigateToAddFriendScreen() {
        val intent = Intent(requireContext(), AddFriendActivity::class.java)
        resultLauncher.launch(intent)
    }

    companion object {
        private const val USER_KEY = "user"
    }
}