package org.sopt.dosopttemplate.data.datasource.remote

import org.sopt.dosopttemplate.data.model.remote.request.RequestLoginDto
import org.sopt.dosopttemplate.data.model.remote.request.RequestSignupDto
import org.sopt.dosopttemplate.data.model.remote.response.ResponseLoginDto

interface AuthDataSource {
    suspend fun login(request: RequestLoginDto): ResponseLoginDto

    suspend fun signup(request: RequestSignupDto): Unit
}