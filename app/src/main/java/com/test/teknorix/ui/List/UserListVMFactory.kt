package com.test.teknorix.ui.List

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.teknorix.repository.Repository

class UserListVMFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(UserListVM::class.java)) {
            UserListVM(repository) as T
        } else {
            throw IllegalArgumentException("Viewmodel not found")
        }
    }
}