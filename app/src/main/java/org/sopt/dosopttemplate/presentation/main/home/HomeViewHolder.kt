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
        private val binding: ItemHomePostBinding
    ) : HomeViewHolder(binding.root) {
        fun onBind(data: HomeModel.FriendInfoModel) {
            binding.data = data
        }
    }

    class BirthdayViewHolder(
        private val binding: ItemHomeBirthdayBinding
    ) : HomeViewHolder(binding.root) {
        fun onBind(data: HomeModel.FriendInfoModel) {
            binding.data = data
        }
    }
}
