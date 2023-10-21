package org.sopt.dosopttemplate.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.dosopttemplate.data.repository.SharedPrefRepositoryImpl
import org.sopt.dosopttemplate.domain.repository.SharedPrefRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindsSharedPrefRepository(
        sharedPrefRepository: SharedPrefRepositoryImpl
    ): SharedPrefRepository
}