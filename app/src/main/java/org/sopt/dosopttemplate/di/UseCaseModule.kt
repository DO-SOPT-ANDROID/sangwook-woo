package org.sopt.dosopttemplate.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.dosopttemplate.domain.usecase.ValidateIdUseCase
import org.sopt.dosopttemplate.domain.usecase.ValidatePasswordUseCase
import org.sopt.dosopttemplate.domain.validator.Validator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    @Singleton
    fun provideValidateIdUseCase(validator: Validator): ValidateIdUseCase {
        return ValidateIdUseCase(validator)
    }

    @Provides
    @Singleton
    fun provideValidatePasswordUseCase(validator: Validator): ValidatePasswordUseCase {
        return ValidatePasswordUseCase(validator)
    }
}