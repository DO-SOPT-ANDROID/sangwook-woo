package org.sopt.dosopttemplate.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import org.sopt.dosopttemplate.data.model.local.FriendInfoEntity

@Dao
interface FriendInfoDao {
    @Query("SELECT * FROM table_friend_info")
    suspend fun getAll(): List<FriendInfoEntity>

    @Insert
    suspend fun addFriend(friendInfoEntity: FriendInfoEntity)

    @Insert
    suspend fun addFriends(friendList: List<FriendInfoEntity>)

    @Delete
    suspend fun deleteFriend(friendInfoEntity: FriendInfoEntity)

    @Query("DELETE FROM table_friend_info WHERE id = :id")
    suspend fun deleteFriendById(id: Int)
}