package org.sopt.dosopttemplate.presentation.main.home

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.FragmentHomeBinding
import org.sopt.dosopttemplate.presentation.main.home.addfriend.AddFriendActivity
import org.sopt.dosopttemplate.presentation.main.home.frienddetail.FriendDetailActivity
import org.sopt.dosopttemplate.presentation.model.HomeModel
import org.sopt.dosopttemplate.presentation.model.UserModel
import org.sopt.dosopttemplate.util.binding.BindingFragment
import org.sopt.dosopttemplate.util.fragment.AlertDialogFragment
import org.sopt.dosopttemplate.util.intent.getParcelable
import org.sopt.dosopttemplate.util.view.UiState
import org.sopt.dosopttemplate.util.view.snackBar

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val viewModel by viewModels<HomeViewModel>()
    lateinit var myInfo: HomeModel.MyInfoModel
    lateinit var homeAdapter: HomeAdapter
    lateinit var homeVpAdapter: HomeVpAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOrientationCheck()
        initFloatingBtnClickListener()
        initFriendDeleteStateObserver()
        initResultLauncher()
        initHomeAdapter()
        initFriendListStateObserver()
        getMyInfo()
        getFriendList()
    }

    private fun initFriendDeleteStateObserver() {
        viewModel.friendDeleteState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    getFriendList()
                }

                is UiState.Failure -> {
                    binding.root.snackBar { state.msg }
                }

                else -> {}
            }
        }
    }

    private fun initFloatingBtnClickListener() {
        binding.btnHomeFloating.setOnClickListener {
            navigateToAddFriendScreen()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                binding.rvHome.visibility = View.VISIBLE
                binding.vpHome.visibility = View.INVISIBLE
            }

            Configuration.ORIENTATION_LANDSCAPE -> {
                binding.vpHome.visibility = View.VISIBLE
                binding.rvHome.visibility = View.INVISIBLE
            }

            else -> {}
        }
    }

    private fun initOrientationCheck() {
        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                binding.rvHome.visibility = View.VISIBLE
                binding.vpHome.visibility = View.INVISIBLE
            }

            Configuration.ORIENTATION_LANDSCAPE -> {
                binding.vpHome.visibility = View.VISIBLE
                binding.rvHome.visibility = View.INVISIBLE
            }

            else -> {}
        }
    }

    private fun initHomeAdapter() {
        runCatching {
            homeAdapter = HomeAdapter(
                onLongClicked = { friend ->
                    showFriendDeleteAlertDialogFragment(friend.id)
                },
                onClicked = { friend ->
                    initHomeAdapterOnClicked(friend)
                }
            )
            binding.rvHome.adapter = homeAdapter
        }.onFailure { t ->
            if (t is IllegalArgumentException) {
                t.printStackTrace()
            }
        }

        runCatching {
            homeVpAdapter = HomeVpAdapter(
                onLongClicked = { friend ->
                    showFriendDeleteAlertDialogFragment(friend.id)
                },
                onClicked = { friend ->
                    initHomeAdapterOnClicked(friend)
                }
            )
            binding.vpHome.adapter = homeVpAdapter
        }.onFailure { t ->
            if (t is IllegalArgumentException) {
                t.printStackTrace()
            }
        }
    }

    private fun initHomeAdapterOnClicked(friend: HomeModel.FriendInfoModel) {
        val intent = Intent(requireContext(), FriendDetailActivity::class.java)
        intent.putExtra(FRIEND_KEY, friend)
        startActivity(intent)
    }

    private fun initResultLauncher() {
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                viewModel.getFriendList()
            }
        }
    }

    private fun initFriendListStateObserver() {
        viewModel.friendListState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    submitHomeList(state)
                    submitHomeVpList(state)
                }

                is UiState.Failure -> {
                    binding.root.snackBar { state.msg }
                }

                else -> {}
            }
        }
    }

    fun scrollTop() {
        binding.rvHome.smoothScrollToPosition(0)
    }

    private fun submitHomeList(state: UiState.Success<List<HomeModel.FriendInfoModel>>) {
        val list = listOf(myInfo) + state.data.sortedBy { it.name }
        homeAdapter.submitList(list)
    }

    private fun submitHomeVpList(state: UiState.Success<List<HomeModel.FriendInfoModel>>) {
        val list = listOf(myInfo) + state.data.sortedBy { it.name }
        homeVpAdapter.submitList(list)
    }

    private fun getMyInfo() {
        val userModel = requireActivity().intent.getParcelable(USER_KEY, UserModel::class.java)
        val myInformation = HomeModel.MyInfoModel(0, userModel?.nickname, userModel?.discription)
        myInfo = myInformation
    }

    private fun getFriendList() {
        viewModel.getFriendList()
    }

    private fun navigateToAddFriendScreen() {
        val intent = Intent(requireContext(), AddFriendActivity::class.java)
        resultLauncher.launch(intent)
    }

    private fun showFriendDeleteAlertDialogFragment(id: Int?) {
        val dialog = AlertDialogFragment.newInstance(
            getString(R.string.home_alert_title),
            getString(R.string.home_alert_negative_label),
            getString(R.string.home_alert_positive_label),
            handleNegativeButton = {
            },
            handlePositiveButton = {
                viewModel.deleteUser(id)
            })
        dialog.show(parentFragmentManager, FRIEND_DELETE_TAG)
    }

    companion object {
        private const val USER_KEY = "user"
        private const val FRIEND_KEY = "friend"
        private const val FRIEND_DELETE_TAG = "friend delete"
    }
}