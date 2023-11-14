package org.sopt.dosopttemplate.data.datasourceimpl.local

import org.sopt.dosopttemplate.data.database.FriendDataBase
import org.sopt.dosopttemplate.data.datasource.local.FriendLocalDataSource
import org.sopt.dosopttemplate.data.model.local.FriendInfoEntity
import javax.inject.Inject

class FriendLocalDataSourceImpl @Inject constructor(
    private val friendDataBase: FriendDataBase
) : FriendLocalDataSource {
    override suspend fun getAll(): List<FriendInfoEntity> {
        return friendDataBase.friendInfoDao().getAll()
    }

    override suspend fun addFriend(friendInfoEntity: FriendInfoEntity) {
        friendDataBase.friendInfoDao().addFriend(friendInfoEntity)
    }

    override suspend fun addFriends(friendList: List<FriendInfoEntity>) {
        friendDataBase.friendInfoDao().addFriends(friendList)
    }

    override suspend fun deleteFriend(friendInfoEntity: FriendInfoEntity) {
        friendDataBase.friendInfoDao().deleteFriend(friendInfoEntity)
    }

    override suspend fun deleteFriendById(id: Int) {
        friendDataBase.friendInfoDao().deleteFriendById(id)
    }


}