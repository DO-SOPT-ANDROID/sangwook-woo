package org.sopt.dosopttemplate.presentation.main.home.frienddetail

import android.os.Bundle
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.ActivityFriendDetailBinding
import org.sopt.dosopttemplate.presentation.model.HomeModel
import org.sopt.dosopttemplate.util.binding.BindingActivity
import org.sopt.dosopttemplate.util.intent.getParcelable

class FriendDetailActivity :
    BindingActivity<ActivityFriendDetailBinding>(R.layout.activity_friend_detail) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val friend = intent.getParcelable(FRIEND_KEY, HomeModel.FriendInfoModel::class.java)
        binding.data = friend
        initBackBtnClickListener()
    }

    private fun initBackBtnClickListener() {
        binding.cvFriendDetailBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        private const val FRIEND_KEY = "friend"
    }
}