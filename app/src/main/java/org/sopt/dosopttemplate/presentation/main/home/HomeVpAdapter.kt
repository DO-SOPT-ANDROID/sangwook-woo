package org.sopt.dosopttemplate.presentation.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.sopt.dosopttemplate.databinding.ItemHomeVpHeaderBinding
import org.sopt.dosopttemplate.databinding.ItemHomeVpPostBinding
import org.sopt.dosopttemplate.presentation.model.HomeModel
import org.sopt.dosopttemplate.presentation.model.HomeModel.FriendInfoModel.Companion.NORMAL_FRIEND_VIEW_TYPE
import org.sopt.dosopttemplate.presentation.model.HomeModel.MyInfoModel.Companion.MY_INFO_VIEW_TYPE
import org.sopt.dosopttemplate.util.view.ItemDiffCallback

class HomeVpAdapter(
    private val onLongClicked: (HomeModel.FriendInfoModel) -> Unit,
    private val onClicked: (HomeModel.FriendInfoModel) -> Unit
) : ListAdapter<HomeModel, HomeVpViewHolder>(DiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeVpViewHolder {
        return when (viewType) {
            MY_INFO_VIEW_TYPE -> {
                HomeVpViewHolder.MyInfoViewHolder(
                    ItemHomeVpHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            NORMAL_FRIEND_VIEW_TYPE -> {
                HomeVpViewHolder.FriendViewHolder(
                    ItemHomeVpPostBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onLongClicked,
                    onClicked
                )
            }

            else -> throw IllegalArgumentException(UNKNOWN_TYPE_EXCEPTION)
        }
    }

    override fun onBindViewHolder(holder: HomeVpViewHolder, position: Int) {
        when (holder) {
            is HomeVpViewHolder.FriendViewHolder -> holder.onBind(currentList[position] as HomeModel.FriendInfoModel)
            is HomeVpViewHolder.MyInfoViewHolder -> holder.onBind(currentList[position] as HomeModel.MyInfoModel)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is HomeModel.MyInfoModel -> {
                MY_INFO_VIEW_TYPE
            }

            is HomeModel.FriendInfoModel -> {
                NORMAL_FRIEND_VIEW_TYPE
            }
        }
    }

    companion object {
        private val DiffUtil = ItemDiffCallback<HomeModel>(
            onItemsTheSame = { old, new -> old.id == new.id },
            onContentsTheSame = { old, new -> old == new }
        )
        private const val UNKNOWN_TYPE_EXCEPTION = "UNKNOWN_TYPE"
    }
}