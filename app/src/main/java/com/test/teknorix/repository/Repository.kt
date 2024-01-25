package com.test.teknorix.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.test.teknorix.repository.api.ApiService
import com.test.teknorix.repository.paging.UsersPagingSource

class Repository(
    private val apiService: ApiService,
) {
    fun getUserPaging() = Pager(
        config = PagingConfig(pageSize = 6, maxSize = 50),
        pagingSourceFactory = { UsersPagingSource(apiService) }
    ).liveData
}