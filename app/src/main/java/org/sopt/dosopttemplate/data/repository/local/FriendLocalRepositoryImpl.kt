package org.sopt.dosopttemplate.data.repository.local

import org.sopt.dosopttemplate.data.datasource.local.FriendLocalDataSource
import org.sopt.dosopttemplate.data.model.local.FriendInfoEntity.Companion.toFriend
import org.sopt.dosopttemplate.data.model.local.FriendInfoEntity.Companion.toFriendInfoEntity
import org.sopt.dosopttemplate.domain.entity.Friend
import org.sopt.dosopttemplate.domain.repository.FriendLocalRepository
import javax.inject.Inject

class FriendLocalRepositoryImpl @Inject constructor(
    private val friendLocalDataSource: FriendLocalDataSource
) : FriendLocalRepository {
    override suspend fun getAll(): Result<List<Friend>> =
        runCatching {
            toFriend(friendLocalDataSource.getAll())
        }

    override suspend fun addFriend(friend: Friend) {
        friendLocalDataSource.addFriend(toFriendInfoEntity(listOf(friend)).first())
    }

    override suspend fun addFriends(friendList: List<Friend>) {
        friendLocalDataSource.addFriends(toFriendInfoEntity(friendList))
    }

    override suspend fun deleteFriend(friend: Friend) {
        friendLocalDataSource.deleteFriend(toFriendInfoEntity(listOf(friend)).first())
    }

    override suspend fun deleteFriendById(id: Int) {
        friendLocalDataSource.deleteFriendById(id)
    }
}