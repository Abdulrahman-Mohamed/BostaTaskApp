package com.abdullrahman.bostataskapp.feature_gallery.presentation.image

import android.content.Intent
import android.graphics.Bitmap
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
            loadImage(args.url)
            binding.share.setOnClickListener {
                val bitmap = binding.touchy.drawable.toBitmap()
                shareImageandText(bitmap)
            }
    }}

    private fun loadImage(urlString: String) {
        val url =
            GlideUrl(urlString, LazyHeaders.Builder().addHeader("User-Agent", "your-user-agent").build())

        Glide.with(requireContext())
            .load(url)
            .fitCenter()
            .into(binding.touchy)
    }

    private fun shareImageandText(bitmap: Bitmap) {
        val uri: Uri? = getmageToShare(bitmap)
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.putExtra(Intent.EXTRA_TEXT, "Bosta")
        intent.putExtra(Intent.EXTRA_SUBJECT, "Bosta sharing image")
        intent.type = "image/png"
        startActivity(Intent.createChooser(intent, "Share Via"))
    }

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
}