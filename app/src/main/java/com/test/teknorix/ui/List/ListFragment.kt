package com.test.teknorix.ui.List

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.test.teknorix.MyApp
import com.test.teknorix.R
import com.test.teknorix.adapter.UserListLoadStateAdapter
import com.test.teknorix.adapter.UsersPagingAdapter
import com.test.teknorix.databinding.FragmentListBinding
import com.test.teknorix.repository.model.User
import com.test.teknorix.repository.preferences.PreferenceManager
import com.test.teknorix.repository.preferences.PrefsHelper
import com.test.teknorix.util.NetworkUtils
import kotlinx.coroutines.launch

class ListFragment : Fragment(), UsersPagingAdapter.ClickListener {

    private lateinit var userListVM: UserListVM
    private lateinit var binding: FragmentListBinding
    private lateinit var livePagingData: LiveData<PagingData<User>>

    private val preferenceHelper: PrefsHelper by lazy { PreferenceManager(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val repository = (context?.applicationContext as MyApp).repository
        userListVM = ViewModelProvider(this, UserListVMFactory(repository)).get(UserListVM::class.java)
        lifecycleScope.launch {
             /**Storing the live paging data from view model in onAttach so that the api call is not
             made once the fragment comes back to foreground from back stack*/
            livePagingData = userListVM.userList()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loadCount = preferenceHelper.getAppLoadCount()

        /**Setting title to Toolbar with the count from shared preferences*/
        binding.toolbar.toolbar.title = getString(R.string.user_title, loadCount)
        binding.recycleView.layoutManager = LinearLayoutManager(context)
        binding.recycleView.setHasFixedSize(true)

        /** Recycler view with Pagination */
        val adapter = UsersPagingAdapter(this)
        adapter.addLoadStateListener { loadState ->
            val isEmptyList = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
            binding.container.isRefreshing = false
            binding.pbLoading.isVisible = false
            binding.btnRetry.isVisible = false
            binding.tvMessage.isVisible = isEmptyList
            binding.tvMessage.text = getString(R.string.text_empty)
            binding.recycleView.isVisible = loadState.refresh is LoadState.NotLoading

            if (loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading) {
                binding.pbLoading.isVisible = true
            } else {
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    binding.recycleView.isVisible = false
//                    binding.tvMessage.text = it.error.toString()
                    binding.tvMessage.isVisible = true
                    binding.btnRetry.isVisible = true
                    if (!NetworkUtils.isInternetAvailable(requireContext())) {
                        binding.tvMessage.text = getString(R.string.no_network)
                    } else {
                        binding.tvMessage.text = getString(R.string.error)
                    }
                }
            }
        }
        binding.recycleView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = UserListLoadStateAdapter { adapter.retry() },
            footer = UserListLoadStateAdapter { adapter.retry() }
        )

        /** observing saved live paging data */
        livePagingData.observe(viewLifecycleOwner) {
            it?.let {
                /** submitting new data to paging adapter */
                adapter.submitData(lifecycle, it)
            }
        }

        binding.btnRetry.setOnClickListener {
            /** retry for paging adapter */
            adapter.retry()
        }

        binding.container.setOnRefreshListener {
            /** pull down to refresh functionality  */
            binding.container.isRefreshing = true
            adapter.refresh()
        }
    }

    override fun onClick(user: User, position: Int) {
        /** navigating to details fragment and passing selected user data */
        val bundle = Bundle()
        bundle.putString("user", Gson().toJson(user))
        findNavController().navigate(R.id.action_listFragment_to_listDetailFragment, bundle)
    }
}