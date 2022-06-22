package com.abdullrahman.bostataskapp.feature_gallery.presentation.gallery.useCases

import android.content.Context
import android.widget.Toast
import com.abdullrahman.bostataskapp.feature_gallery.domain.models.ImagesItem
import com.abdullrahman.bostataskapp.feature_gallery.domain.utils.Response
import com.abdullrahman.bostataskapp.feature_gallery.repository.MainRepoImp
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(
    @ApplicationContext val context: Context,
    val mainRepo: MainRepoImp
) {
    suspend operator fun invoke(id: Long): List<ImagesItem?>? {
        var images: List<ImagesItem?>? = null
        when (val result = mainRepo.getPhotos(id)) {
            is Response.Success -> {
                val imagesList = result.data
                images = imagesList
            }
            is Response.Error -> {
                Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
            }
        }
        return images

    }
}