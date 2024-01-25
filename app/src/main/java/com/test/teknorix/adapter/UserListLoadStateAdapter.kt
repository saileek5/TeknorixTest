package com.test.teknorix.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.test.teknorix.databinding.ItemHeaderFooterBinding

class UserListLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<UserListLoadStateAdapter.ViewHolder>(){
    inner class ViewHolder(private val binding: ItemHeaderFooterBinding, retry: () -> Unit) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener { retry() }
        }

        fun bind(loadState: LoadState) = with(binding) {
            if (loadState is LoadState.Error) {
                binding.tvMessage.text = loadState.error.localizedMessage
            }
            binding.pbLoading.isVisible = loadState is LoadState.Loading
            binding.btnRetry.isVisible = loadState is LoadState.Error
            binding.tvMessage.isVisible = loadState is LoadState.Error
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) = holder.bind(loadState)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) = ViewHolder(
        ItemHeaderFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        retry
    )
}