package org.sopt.dosopttemplate.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.dosopttemplate.data.datasourceimpl.local.FriendLocalDataSourceImpl
import org.sopt.dosopttemplate.data.datasourceimpl.local.SharedPrefDataSourceImpl
import org.sopt.dosopttemplate.data.datasource.local.FriendLocalDataSource
import org.sopt.dosopttemplate.data.datasource.local.SharedPrefDataSource
import org.sopt.dosopttemplate.data.datasource.remote.AuthDataSource
import org.sopt.dosopttemplate.data.datasource.remote.ReqresDataSource
import org.sopt.dosopttemplate.data.datasourceimpl.remote.AuthDataSourceImpl
import org.sopt.dosopttemplate.data.datasourceimpl.remote.ReqresDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun bindsSharedPrefDataSource(sharedPrefDataSource: SharedPrefDataSourceImpl): SharedPrefDataSource

    @Singleton
    @Binds
    abstract fun bindsFriendLocalDataSource(friendLocalDataSource: FriendLocalDataSourceImpl): FriendLocalDataSource

    @Singleton
    @Binds
    abstract fun bindsAuthDataSource(authDataSource: AuthDataSourceImpl): AuthDataSource

    @Singleton
    @Binds
    abstract fun bindsReqresDataSource(reqresDataSource: ReqresDataSourceImpl): ReqresDataSource
}