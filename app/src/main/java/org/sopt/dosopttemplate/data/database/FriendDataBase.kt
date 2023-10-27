package org.sopt.dosopttemplate.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import org.sopt.dosopttemplate.data.model.local.FriendInfoEntity

@Database(
    entities = [
        FriendInfoEntity::class
    ], version = 1
)
abstract class FriendDataBase : RoomDatabase() {
    abstract fun friendInfoDao(): FriendInfoDao
}