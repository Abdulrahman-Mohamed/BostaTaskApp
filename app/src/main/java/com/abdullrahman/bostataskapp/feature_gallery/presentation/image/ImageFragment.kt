package com.abdullrahman.bostataskapp.feature_gallery.presentation.image

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.abdullrahman.bostataskapp.R
import com.abdullrahman.bostataskapp.databinding.FragmentImageBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import java.io.File
import java.io.FileOutputStream

class ImageFragment : Fragment() {
   lateinit var binding:FragmentImageBinding
    val args: ImageFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_image, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (args != null){
            binding.tvRetry.setOnClickListener {
                loadImage(args.url)
            }
            loadImage(args.url)
            binding.share.setOnClickListener {
                val bitmap = binding.touchy.drawable.toBitmap()
                shareImageandText(bitmap)
            }
    }}

    /*Load the image after checking the internet*/
    private fun loadImage(urlString: String) {
        if (isNetworkConnected() && internetIsConnected()) {
            val url =
                GlideUrl(
                    urlString,
                    LazyHeaders.Builder().addHeader("User-Agent", "your-user-agent").build()
                )

            Glide.with(requireContext())
                .load(url)
                .fitCenter()
                .into(binding.touchy)
            binding.touchy.visibility = View.VISIBLE
            binding.share.visibility = View.VISIBLE
            binding.tvRetry.visibility = View.GONE
        }else{
            Toast.makeText(requireContext(),"Check internet Connection",Toast.LENGTH_SHORT).show()
            binding.touchy.visibility = View.GONE
            binding.share.visibility = View.GONE
            binding.tvRetry.visibility = View.VISIBLE

        }
    }
    /*prepare intent params for sharing the image and starting a service of sharing to available resources*/
    private fun shareImageandText(bitmap: Bitmap) {
        val uri: Uri? = getmageToShare(bitmap)
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.putExtra(Intent.EXTRA_TEXT, "Bosta")
        intent.putExtra(Intent.EXTRA_SUBJECT, "Bosta sharing image")
        intent.type = "image/png"
        startActivity(Intent.createChooser(intent, "Share Via"))
    }
    /* saving the image temporarly into a cash storage as png in order of sharing it*/
    private fun getmageToShare(bitmap: Bitmap): Uri? {
        val imagefolder = File(requireActivity().cacheDir, "images")
        var uri: Uri? = null
        try {
            imagefolder.mkdirs()
            val file = File(imagefolder, "Bosta.png")
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream)
            outputStream.flush()
            outputStream.close()
            uri = FileProvider.getUriForFile(requireContext(), "com.abdullrahman.bostataskapp.fileprovider", file)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "" + e.message, Toast.LENGTH_LONG).show()
        }
        return uri
    }
    /*checking if device has a connection to a certain network*/
    private fun isNetworkConnected(): Boolean {
        val cm = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }
    /*checking if device has a internet connection*/
    fun internetIsConnected(): Boolean {
        return try {
            val command = "ping -c 1 google.com"
            Runtime.getRuntime().exec(command).waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }
}