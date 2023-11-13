package org.sopt.dosopttemplate.data.service

import org.sopt.dosopttemplate.data.model.remote.request.RequestLoginDto
import org.sopt.dosopttemplate.data.model.remote.response.ResponseLoginDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/v1/members/sign-in")
    fun login(
        @Body request: RequestLoginDto
    ): Call<ResponseLoginDto>
}