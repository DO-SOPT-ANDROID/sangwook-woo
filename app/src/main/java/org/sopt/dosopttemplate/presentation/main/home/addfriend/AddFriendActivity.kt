package org.sopt.dosopttemplate.presentation.main.home.addfriend

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.ActivityAddFriendBinding
import org.sopt.dosopttemplate.util.activity.hideKeyboard
import org.sopt.dosopttemplate.util.binding.BindingActivity
import org.sopt.dosopttemplate.util.view.UiState
import org.sopt.dosopttemplate.util.view.snackBar
import java.time.LocalDate

@AndroidEntryPoint
class AddFriendActivity : BindingActivity<ActivityAddFriendBinding>(R.layout.activity_add_friend) {
    private val viewModel by viewModels<AddFriendViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.data = viewModel
        initCalenderBtnClikcListener()
        initFriendAddBtnClickListener()
        initAddFriendStateObserver()
        initHideKeyboard()
    }

    private fun initAddFriendStateObserver() {
        viewModel.addFriendState.observe(this) { state ->
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
        }
    }

    private fun initFriendAddBtnClickListener() {
        binding.btnAddFriend.setOnClickListener {
            viewModel.addFriend()
        }
    }

    private fun initCalenderBtnClikcListener() {
        binding.ivAddFriendBirthday.setOnClickListener {
            showDatePicker()
        }
    }

    private fun showDatePicker() {
        val now = LocalDate.now()

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = LocalDate.of(selectedYear, selectedMonth + 1, selectedDay)
                binding.tvAddFriendBirthdayInput.text = "$selectedDate"
            },
            now.year,
            now.monthValue - 1,
            now.dayOfMonth
        )

        datePickerDialog.show()
    }

    private fun initHideKeyboard() {
        binding.root.setOnClickListener { hideKeyboard() }
    }

    companion object {
        private const val NAME_ERROR = "name error"
        private const val BIRTHDAY_ERROR = "birthday error"
    }
}