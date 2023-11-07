package org.sopt.dosopttemplate.presentation.main.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.sopt.dosopttemplate.databinding.ItemHomeVpHeaderBinding
import org.sopt.dosopttemplate.databinding.ItemHomeVpPostBinding
import org.sopt.dosopttemplate.presentation.model.HomeModel

sealed class HomeVpViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class MyInfoViewHolder(
        private val binding: ItemHomeVpHeaderBinding
    ) : HomeVpViewHolder(binding.root) {
        fun onBind(data: HomeModel.MyInfoModel) {
            binding.data = data
        }
    }

    class FriendViewHolder(
        private val binding: ItemHomeVpPostBinding,
        private val onLongClicked: (HomeModel.FriendInfoModel) -> Unit,
        private val onClicked: (HomeModel.FriendInfoModel) -> Unit
    ) : HomeVpViewHolder(binding.root) {
        fun onBind(data: HomeModel.FriendInfoModel) {
            binding.data = data
            binding.root.setOnLongClickListener {
                onLongClicked(data)
                return@setOnLongClickListener true
            }
            binding.root.setOnClickListener {
                onClicked(data)
            }
        }
    }
}
