package org.sopt.dosopttemplate.domain.repository

import org.sopt.dosopttemplate.domain.entity.Friend

interface FriendLocalRepository {
    suspend fun getAll(): Result<List<Friend>>
    suspend fun addFriend(friend: Friend)
    suspend fun addFriends(friendList: List<Friend>)
    suspend fun deleteFriend(friend: Friend)
    suspend fun deleteFriendById(id: Int)
}