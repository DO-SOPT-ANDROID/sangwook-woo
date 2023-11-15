package org.sopt.dosopttemplate.presentation.main.home

import androidx.recyclerview.widget.RecyclerView
import org.sopt.dosopttemplate.databinding.ItemHomeVpPostBinding
import org.sopt.dosopttemplate.domain.entity.ReqresUser

class HomeVpViewHolder (
    private val binding: ItemHomeVpPostBinding,
    private val onLongClicked: (ReqresUser) -> Unit,
    private val onClicked: (ReqresUser) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(data: ReqresUser?) {
        binding.data = data
        if (data == null) return

        binding.root.setOnLongClickListener {
            onLongClicked(data)
            return@setOnLongClickListener true
        }
        binding.root.setOnClickListener {
            onClicked(data)
        }
    }
}