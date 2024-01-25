package com.test.teknorix.ui.List

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.test.teknorix.R
import com.test.teknorix.databinding.FragmentListDetailBinding
import com.test.teknorix.repository.model.User

class ListDetailFragment : Fragment() {
    private lateinit var binding : FragmentListDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListDetailBinding.inflate(layoutInflater, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val string = arguments?.getString("user")
        val user =Gson().fromJson(string, User::class.java)
        binding.toolbar.toolbar.title = user.first_name + " " + user.last_name
        binding.toolbar.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow)
        binding.toolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.tvFirstName.text = user.first_name
        binding.tvLastName.text = user.last_name
        binding.tvEmail.text = user.email
        Picasso.get()
            .load(user.avatar)
            .placeholder(R.drawable.ic_launcher_background)
            .error(com.google.android.material.R.drawable.mtrl_ic_error)
            .into(binding.ivAvatar)
    }
}