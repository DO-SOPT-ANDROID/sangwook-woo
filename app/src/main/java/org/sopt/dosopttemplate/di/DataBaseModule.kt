package org.sopt.dosopttemplate.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.sopt.dosopttemplate.data.database.FriendDataBase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Singleton
    @Provides
    fun provideDataBase(
        @ApplicationContext context: Context
    ): FriendDataBase =
        Room.databaseBuilder(context, FriendDataBase::class.java, "sopt_friend.db")
            .createFromAsset("databases/sopt_friend.db").build()

    @Singleton
    @Provides
    fun provideFriendDao(friendDataBase: FriendDataBase) = friendDataBase.friendInfoDao()
}

