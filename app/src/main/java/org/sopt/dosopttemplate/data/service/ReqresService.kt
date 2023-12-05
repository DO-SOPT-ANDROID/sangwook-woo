package org.sopt.dosopttemplate.data.service

import org.sopt.dosopttemplate.data.model.remote.response.ResponseListUsersDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ReqresService {
    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int
    ): ResponseListUsersDto
}