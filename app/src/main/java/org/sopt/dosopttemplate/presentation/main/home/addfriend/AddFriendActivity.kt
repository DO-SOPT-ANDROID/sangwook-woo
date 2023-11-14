package org.sopt.dosopttemplate.presentation.main.home.addfriend

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.ActivityAddFriendBinding
import org.sopt.dosopttemplate.util.activity.hideKeyboard
import org.sopt.dosopttemplate.util.binding.BindingActivity
import org.sopt.dosopttemplate.util.view.UiState
import org.sopt.dosopttemplate.util.view.snackBar
import java.text.SimpleDateFormat
import java.util.Date

@AndroidEntryPoint
class AddFriendActivity : BindingActivity<ActivityAddFriendBinding>(R.layout.activity_add_friend) {
    private val viewModel by viewModels<AddFriendViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.data = viewModel
        initCalenderBtnClickListener()
        initFriendAddBtnClickListener()
        initAddFriendStateObserver()
        initHideKeyboard()
    }

    private fun initAddFriendStateObserver() {
        viewModel.addFriendState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Success -> {
                    setResult(RESULT_OK, intent)
                    finish()
                }

                is UiState.Failure -> {
                    when (state.msg) {
                        NAME_ERROR -> {
                            binding.root.snackBar { getString(R.string.add_friend_name_error) }
                        }

                        BIRTHDAY_ERROR -> {
                            binding.root.snackBar { getString(R.string.add_friend_birthday_error) }
                        }
                    }
                }

                else -> {
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun initFriendAddBtnClickListener() {
        binding.btnAddFriend.setOnClickListener {
            viewModel.addFriend()
        }
    }

    private fun initCalenderBtnClickListener() {
        binding.ivAddFriendBirthday.setOnClickListener {
            showDatePicker()
        }
    }

    private fun showDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date").build()

        datePicker.addOnPositiveButtonClickListener {
            val date = Date()
            date.time = it
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            binding.tvAddFriendBirthdayInput.text = simpleDateFormat.format(date).toString()
        }

        datePicker.show(supportFragmentManager, datePicker.tag)
    }

    private fun initHideKeyboard() {
        binding.root.setOnClickListener { hideKeyboard() }
    }

    companion object {
        private const val NAME_ERROR = "name error"
        private const val BIRTHDAY_ERROR = "birthday error"
    }
}