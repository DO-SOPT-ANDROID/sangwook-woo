package org.sopt.dosopttemplate.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.dosopttemplate.data.datasource.SharedPrefDataSourceImpl
import org.sopt.dosopttemplate.data.remote.SharedPrefDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun bindsSharedPrefDataSource(sharedPrefDataSource: SharedPrefDataSourceImpl): SharedPrefDataSource
}