package org.sopt.dosopttemplate.presentation.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.sopt.dosopttemplate.databinding.ItemHomeBirthdayBinding
import org.sopt.dosopttemplate.databinding.ItemHomeHeaderBinding
import org.sopt.dosopttemplate.databinding.ItemHomePostBinding
import org.sopt.dosopttemplate.presentation.model.HomeModel
import org.sopt.dosopttemplate.presentation.model.HomeModel.FriendInfoModel.Companion.BIRTHDAY_FRIEND_VIEW_TYPE
import org.sopt.dosopttemplate.presentation.model.HomeModel.FriendInfoModel.Companion.NORMAL_FRIEND_VIEW_TYPE
import org.sopt.dosopttemplate.presentation.model.HomeModel.MyInfoModel.Companion.MY_INFO_VIEW_TYPE
import org.sopt.dosopttemplate.util.view.ItemDiffCallback
import java.time.LocalDate

class HomeAdapter : ListAdapter<HomeModel, HomeViewHolder>(DiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return when (viewType) {
            MY_INFO_VIEW_TYPE -> {
                HomeViewHolder.MyInfoViewHolder(
                    ItemHomeHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            NORMAL_FRIEND_VIEW_TYPE -> {
                HomeViewHolder.FriendViewHolder(
                    ItemHomePostBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            BIRTHDAY_FRIEND_VIEW_TYPE -> {
                HomeViewHolder.BirthdayViewHolder(
                    ItemHomeBirthdayBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> throw Exception(UNKNOWN_TYPE_EXCEPTION)
        }
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        when (holder) {
            is HomeViewHolder.FriendViewHolder -> holder.onBind(currentList[position] as HomeModel.FriendInfoModel)
            is HomeViewHolder.BirthdayViewHolder -> holder.onBind(currentList[position] as HomeModel.FriendInfoModel)
            is HomeViewHolder.MyInfoViewHolder -> holder.onBind(currentList[position] as HomeModel.MyInfoModel)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is HomeModel.MyInfoModel -> {
                MY_INFO_VIEW_TYPE
            }

            is HomeModel.FriendInfoModel -> {
                val friendInfoModel = currentList[position] as HomeModel.FriendInfoModel
                if (isBirthday(friendInfoModel.birthday)) {
                    BIRTHDAY_FRIEND_VIEW_TYPE
                } else {
                    NORMAL_FRIEND_VIEW_TYPE
                }
            }
        }
    }

    private fun isBirthday(birthday: LocalDate?) =
        (birthday?.monthValue == LocalDate.now().monthValue && birthday.dayOfMonth == LocalDate.now().dayOfMonth)


    companion object {
        private val DiffUtil = ItemDiffCallback<HomeModel>(
            onItemsTheSame = { old, new -> old.id == new.id },
            onContentsTheSame = { old, new -> old.id == new.id }
        )
        private const val UNKNOWN_TYPE_EXCEPTION = "UNKNOWN_TYPE"
    }
}