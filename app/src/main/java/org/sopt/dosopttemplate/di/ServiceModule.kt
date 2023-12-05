package org.sopt.dosopttemplate.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.dosopttemplate.data.service.AuthService
import org.sopt.dosopttemplate.data.service.ReqresService
import org.sopt.dosopttemplate.di.qualifier.AUTH
import org.sopt.dosopttemplate.di.qualifier.REQRES
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideAuthService(@AUTH retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideReqresService(@REQRES retrofit: Retrofit): ReqresService =
        retrofit.create(ReqresService::class.java)
}