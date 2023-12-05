package org.sopt.dosopttemplate.presentation.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import org.sopt.dosopttemplate.databinding.ItemHomeVpPostBinding
import org.sopt.dosopttemplate.domain.entity.ReqresUser
import org.sopt.dosopttemplate.util.view.ItemDiffCallback

class HomeVpAdapter(
    private val onLongClicked: (ReqresUser) -> Unit,
    private val onClicked: (ReqresUser) -> Unit
) : PagingDataAdapter<ReqresUser, HomeVpViewHolder>(DiffUtil) {
    override fun onBindViewHolder(holder: HomeVpViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeVpViewHolder {
        return HomeVpViewHolder(
            ItemHomeVpPostBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onLongClicked,
            onClicked
        )
    }
    companion object {
        private val DiffUtil = ItemDiffCallback<ReqresUser>(
            onItemsTheSame = { old, new -> old.id == new.id },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}