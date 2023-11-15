package org.sopt.dosopttemplate.data.repository.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.sopt.dosopttemplate.data.datasource.remote.ReqresDataSource
import org.sopt.dosopttemplate.data.pagingsource.UsersPagingSource
import org.sopt.dosopttemplate.domain.entity.ReqresUser
import org.sopt.dosopttemplate.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val reqresDataSource: ReqresDataSource
) : UserRepository {
    override fun getUser(): Flow<PagingData<ReqresUser>> {
        return Pager(PagingConfig(6, 2)) {
            UsersPagingSource(reqresDataSource)
        }.flow
    }
}