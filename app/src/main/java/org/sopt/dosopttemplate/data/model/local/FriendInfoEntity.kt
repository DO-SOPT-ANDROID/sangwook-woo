package org.sopt.dosopttemplate.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.sopt.dosopttemplate.domain.entity.Friend
import java.time.LocalDate

@Entity(tableName = "table_friend_info")
data class FriendInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String,
    val birthday: String,
    val music: String?,
    @ColumnInfo(name = "image_uri")
    val imageUri: String?,
) {
}
