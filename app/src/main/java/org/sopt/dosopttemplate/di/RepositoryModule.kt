package org.sopt.dosopttemplate.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.dosopttemplate.data.repository.FriendLocalRepositoryImpl
import org.sopt.dosopttemplate.data.repository.SharedPrefRepositoryImpl
import org.sopt.dosopttemplate.data.repository.AuthRepositoryImpl
import org.sopt.dosopttemplate.data.repository.UserRepositoryImpl
import org.sopt.dosopttemplate.domain.repository.AuthRepository
import org.sopt.dosopttemplate.domain.repository.FriendLocalRepository
import org.sopt.dosopttemplate.domain.repository.SharedPrefRepository
import org.sopt.dosopttemplate.domain.repository.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindsSharedPrefRepository(
        sharedPrefRepository: SharedPrefRepositoryImpl
    ): SharedPrefRepository

    @Singleton
    @Binds
    abstract fun bindsFriendLocalRepository(
        friendLocalRepository: FriendLocalRepositoryImpl
    ): FriendLocalRepository

    @Singleton
    @Binds
    abstract fun bindsAuthRepository(
        authRepository: AuthRepositoryImpl
    ): AuthRepository

    @Singleton
    @Binds
    abstract fun bindsUserRepository(
        userRepository: UserRepositoryImpl
    ): UserRepository
}