package org.sopt.dosopttemplate.presentation.model

import android.os.Parcelable
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.parcelize.Parcelize
import org.sopt.dosopttemplate.domain.entity.Friend
import org.sopt.dosopttemplate.domain.entity.ReqresUser
import java.time.LocalDate

sealed class HomeModel {
    abstract val id: Int?

    data class MyInfoModel(
        override val id: Int?,
        val name: String?,
        val discription: String?,
    ) : HomeModel() {
        companion object {
            const val MY_INFO_VIEW_TYPE = 0
        }
    }

    @Parcelize
    data class UserInfoModel(
        override val id: Int?,
        val firstName: String?,
        val lastName: String?,
        val email: String?,
        val avatar: String?
    ): HomeModel(), Parcelable {
        companion object {

            fun toUserInfoModel(userList: PagingData<ReqresUser>) = userList.map { data ->
                UserInfoModel(
                    id = data.id,
                    firstName = data.firstName,
                    lastName = data.lastName,
                    email = data.email,
                    avatar = data.avatar
                )
            }

            fun toReqresUser(userInfoList: List<UserInfoModel>) = userInfoList.map { data ->
                ReqresUser(
                    id = data.id,
                    firstName = data.firstName,
                    lastName = data.lastName,
                    email = data.email,
                    avatar = data.avatar
                )
            }
        }
    }
    @Parcelize
    data class FriendInfoModel(
        override val id: Int?,
        val name: String,
        val birthday: LocalDate?,
        val music: String?,
        val imageUri: String?,
    ) : HomeModel(), Parcelable {
        companion object {
            const val NORMAL_FRIEND_VIEW_TYPE = 1
            const val BIRTHDAY_FRIEND_VIEW_TYPE = 2

            fun toFriendInfoModel(friendList: List<Friend>) = friendList.map { data ->
                FriendInfoModel(
                    id = data.id,
                    name = data.name,
                    birthday = data.birthday,
                    music = data.music,
                    imageUri = data.imageUri
                )
            }

            fun toFriend(friendInfoList: List<FriendInfoModel>) = friendInfoList.map { data ->
                Friend(
                    id = data.id,
                    name = data.name,
                    birthday = data.birthday,
                    music = data.music,
                    imageUri = data.imageUri
                )
            }
        }
    }
}
