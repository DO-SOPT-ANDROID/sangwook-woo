package org.sopt.dosopttemplate.presentation.main.home

import androidx.recyclerview.widget.RecyclerView
import org.sopt.dosopttemplate.databinding.ItemHomePostBinding
import org.sopt.dosopttemplate.domain.entity.ReqresUser

class HomeViewHolder(
    private val binding: ItemHomePostBinding,
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