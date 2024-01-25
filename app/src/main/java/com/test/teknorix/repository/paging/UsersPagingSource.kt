package com.test.teknorix.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.teknorix.repository.model.User
import com.test.teknorix.repository.api.ApiService
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

class UsersPagingSource(private val apiService: ApiService) :
    PagingSource<Int, User>() {
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getUserList(page)

            return LoadResult.Page(
                data = response.body()!!.data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page == response.body()!!.total_pages) null else page + 1
            )
        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        } catch (e: UnknownHostException) {
            return LoadResult.Error(e)
        }
    }
}