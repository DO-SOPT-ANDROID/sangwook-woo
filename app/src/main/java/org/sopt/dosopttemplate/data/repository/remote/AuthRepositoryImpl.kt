package org.sopt.dosopttemplate.data.repository.remote

import org.sopt.dosopttemplate.data.datasource.remote.AuthDataSource
import org.sopt.dosopttemplate.data.datasourceimpl.remote.AuthDataSourceImpl
import org.sopt.dosopttemplate.data.model.remote.request.RequestLoginDto
import org.sopt.dosopttemplate.data.model.remote.request.RequestSignupDto
import org.sopt.dosopttemplate.domain.entity.User
import org.sopt.dosopttemplate.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
): AuthRepository {
    override suspend fun login(user: User): Result<User?> =
        runCatching {
            authDataSource.login(RequestLoginDto.toReqLoginDto(user)).toUser()
        }

    override suspend fun signup(user: User): Result<Unit> =
        runCatching {
            authDataSource.signup(RequestSignupDto.toReqSignupDto(user))
        }

}