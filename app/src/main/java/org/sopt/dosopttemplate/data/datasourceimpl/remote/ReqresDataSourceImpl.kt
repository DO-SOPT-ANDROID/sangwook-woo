package org.sopt.dosopttemplate.data.datasourceimpl.remote

import org.sopt.dosopttemplate.data.datasource.remote.ReqresDataSource
import org.sopt.dosopttemplate.data.model.remote.response.ResponseListUsersDto
import org.sopt.dosopttemplate.data.service.ReqresService
import javax.inject.Inject

class ReqresDataSourceImpl @Inject constructor(
    private val reqresService: ReqresService
): ReqresDataSource {
    override suspend fun getUsers(page: Int): ResponseListUsersDto = reqresService.getUsers(page)
}