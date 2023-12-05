package org.sopt.dosopttemplate.presentation.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import org.sopt.dosopttemplate.databinding.ItemHomePostBinding
import org.sopt.dosopttemplate.domain.entity.ReqresUser
import org.sopt.dosopttemplate.util.view.ItemDiffCallback

class HomeAdapter(
    private val onLongClicked: (ReqresUser) -> Unit,
    private val onClicked: (ReqresUser) -> Unit
) : PagingDataAdapter<ReqresUser, HomeViewHolder>(DiffUtil) {
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            ItemHomePostBinding.inflate(LayoutInflater.from(parent.context), parent, false),
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