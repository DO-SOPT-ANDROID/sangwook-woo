package org.sopt.dosopttemplate.presentation.main.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.sopt.dosopttemplate.databinding.ItemHomeBirthdayBinding
import org.sopt.dosopttemplate.databinding.ItemHomeHeaderBinding
import org.sopt.dosopttemplate.databinding.ItemHomePostBinding
import org.sopt.dosopttemplate.presentation.model.HomeModel

sealed class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class MyInfoViewHolder(
        private val binding: ItemHomeHeaderBinding
    ) : HomeViewHolder(binding.root) {
        fun onBind(data: HomeModel.MyInfoModel) {
            binding.data = data
        }
    }

    class FriendViewHolder(
        private val binding: ItemHomePostBinding,
        private val onLongClicked: (HomeModel.FriendInfoModel) -> Unit,
        private val onClicked: (HomeModel.FriendInfoModel) -> Unit
    ) : HomeViewHolder(binding.root) {
        fun onBind(data: HomeModel.FriendInfoModel) {
            binding.data = data
            binding.root.setOnLongClickListener {
                onLongClicked(data)
                return@setOnLongClickListener true
            }
            binding.root.setOnClickListener {
                onClicked(data)
            }
            binding.executePendingBindings()
        }
    }

    class BirthdayViewHolder(
        private val binding: ItemHomeBirthdayBinding,
        private val onLongClicked: (HomeModel.FriendInfoModel) -> Unit,
        private val onClicked: (HomeModel.FriendInfoModel) -> Unit
    ) : HomeViewHolder(binding.root) {
        fun onBind(data: HomeModel.FriendInfoModel) {
            binding.data = data
            binding.root.setOnLongClickListener {
                onLongClicked(data)
                return@setOnLongClickListener true
            }
            binding.root.setOnClickListener {
                onClicked(data)
            }
            binding.executePendingBindings()
        }
    }
}
