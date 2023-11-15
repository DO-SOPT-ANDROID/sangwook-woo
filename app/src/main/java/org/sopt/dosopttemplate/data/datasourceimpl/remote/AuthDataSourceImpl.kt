package org.sopt.dosopttemplate.data.datasourceimpl.remote

import org.sopt.dosopttemplate.data.datasource.remote.AuthDataSource
import org.sopt.dosopttemplate.data.model.remote.request.RequestLoginDto
import org.sopt.dosopttemplate.data.model.remote.request.RequestSignupDto
import org.sopt.dosopttemplate.data.model.remote.response.ResponseLoginDto
import org.sopt.dosopttemplate.data.service.AuthService
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService
): AuthDataSource {
    override suspend fun login(request: RequestLoginDto): ResponseLoginDto = authService.login(request)

    override suspend fun signup(request: RequestSignupDto): Unit = authService.signup(request)

}