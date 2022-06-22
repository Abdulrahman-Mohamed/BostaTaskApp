package com.abdullrahman.bostataskapp.feature_gallery.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.abdullrahman.bostataskapp.R
import com.abdullrahman.bostataskapp.databinding.FragmentProfileBinding
import com.abdullrahman.bostataskapp.feature_gallery.domain.models.AlbumsItem
import com.abdullrahman.bostataskapp.feature_gallery.domain.recycler.onCLick.AlbumsOnClick
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(), AlbumsOnClick {
    lateinit var binding: FragmentProfileBinding
    lateinit var viewModel: ProfileViewModel
    lateinit var nav: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.viewModel = viewModel
        binding.listener = this
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav = findNavController()
        binding.root.visibility = View.GONE
        checkConnection()
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (!it)
                binding.root.visibility = View.VISIBLE
        }

        binding.tvRetry.setOnClickListener {
            viewModel.execute()
        }
    }

    /*check internet connection*/
    private fun checkConnection() {
        viewModel.noConnection.observe(viewLifecycleOwner) {
            if (it) {
                binding.tvRetry.visibility = View.VISIBLE
                binding.textView.visibility = View.GONE
                binding.textView2.visibility = View.GONE

            } else {
                binding.tvRetry.visibility = View.GONE
                binding.textView.visibility = View.VISIBLE
                binding.textView2.visibility = View.VISIBLE
            }
        }
    }
    /*on item clicked in the recycler pass album_id and navigate to next fragment*/
    override fun onClick(album: AlbumsItem) {
        val action = ProfileFragmentDirections.actionProfileFragmentToGalleryFragment(album.id!!)
        nav.navigate(action)
    }
}