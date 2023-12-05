package org.sopt.dosopttemplate.data.datasource.local

import org.sopt.dosopttemplate.data.model.local.FriendInfoEntity

interface FriendLocalDataSource {
    suspend fun getAll(): List<FriendInfoEntity>
    suspend fun addFriend(friendInfoEntity: FriendInfoEntity)
    suspend fun addFriends(friendList: List<FriendInfoEntity>)
    suspend fun deleteFriend(friendInfoEntity: FriendInfoEntity)
    suspend fun deleteFriendById(id: Int)
}