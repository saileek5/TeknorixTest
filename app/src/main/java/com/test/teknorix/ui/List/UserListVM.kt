package com.test.teknorix.ui.List

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test.teknorix.repository.Repository
import com.test.teknorix.repository.model.User
import com.test.teknorix.repository.model.UserListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserListVM(private val repository: Repository) : ViewModel() {
    fun userList() : LiveData<PagingData<User>> {
        return repository.getUserPaging().cachedIn(viewModelScope)
    }
}