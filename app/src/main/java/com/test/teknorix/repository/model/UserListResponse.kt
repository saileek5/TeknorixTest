package com.test.teknorix.repository.model

import com.test.teknorix.repository.model.Support

data class UserListResponse(
    val data: List<User>,
    val page: Int,
    val per_page: Int,
    val support: Support,
    val total: Int,
    val total_pages: Int
)