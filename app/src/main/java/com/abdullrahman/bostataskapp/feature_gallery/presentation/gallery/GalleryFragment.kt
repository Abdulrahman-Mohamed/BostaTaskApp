package com.abdullrahman.bostataskapp.feature_gallery.presentation.gallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.abdullrahman.bostataskapp.R
import com.abdullrahman.bostataskapp.databinding.FragmentGalleryBinding
import com.abdullrahman.bostataskapp.feature_gallery.domain.models.ImagesItem
import com.abdullrahman.bostataskapp.feature_gallery.domain.recycler.onCLick.ImagesOnClick
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment(), ImagesOnClick {
    val args: GalleryFragmentArgs by navArgs()
    lateinit var binding: FragmentGalleryBinding
    lateinit var viewModel: GalleryViewModel
    lateinit var nav:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false)
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
        if (args != null) {
            viewModel.getImagesList(args.id)
            binding.tvRetry.setOnClickListener {
                viewModel.getImagesList(args.id)
            }
            }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (!it) {
                binding.root.visibility = View.VISIBLE

            }
        }

    }

    private fun checkConnection() {
      viewModel.noConnection.observe(viewLifecycleOwner){
          if (it){
              binding.tvRetry.visibility = View.VISIBLE
              binding.etSearchBar.visibility = View.GONE
              binding.rvImages.visibility = View.GONE
          }else
          {
              binding.tvRetry.visibility = View.GONE
              binding.etSearchBar.visibility = View.VISIBLE
              binding.rvImages.visibility = View.VISIBLE
          }
      }

    }

    override fun onClick(images: ImagesItem) {
       val action = GalleryFragmentDirections.actionGalleryFragmentToImageFragment(images.url!!)
        binding.etSearchBar.setText(null)

        nav.navigate(action)
    }


}