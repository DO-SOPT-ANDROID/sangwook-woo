package org.sopt.dosopttemplate.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.sopt.dosopttemplate.data.datasource.remote.ReqresDataSource
import org.sopt.dosopttemplate.domain.entity.ReqresUser
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UsersPagingSource @Inject constructor(
    private val reqresDataSource: ReqresDataSource
) : PagingSource<Int,ReqresUser>() {
    override fun getRefreshKey(state: PagingState<Int, ReqresUser>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReqresUser> {
        return try {
            val currentPage = params.key ?: 1
            val users = reqresDataSource.getUsers(
                currentPage
            ).toReqresUser()
            LoadResult.Page(
                data = users,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (users.isEmpty()) null else currentPage + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}