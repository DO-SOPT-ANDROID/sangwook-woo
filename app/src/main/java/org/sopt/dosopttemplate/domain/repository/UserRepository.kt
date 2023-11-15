package org.sopt.dosopttemplate.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.sopt.dosopttemplate.domain.entity.ReqresUser

interface UserRepository {
    fun getUser(): Flow<PagingData<ReqresUser>>
}