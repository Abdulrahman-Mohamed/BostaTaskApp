package com.abdullrahman.bostataskapp.feature_gallery.presentation.profile.useCases

import android.content.Context
import android.widget.Toast
import com.abdullrahman.bostataskapp.feature_gallery.domain.models.AlbumsItem
import com.abdullrahman.bostataskapp.feature_gallery.domain.models.User
import com.abdullrahman.bostataskapp.feature_gallery.domain.utils.Response
import com.abdullrahman.bostataskapp.feature_gallery.repository.MainRepoImp
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetAlbumsUseCase@Inject constructor(
    @ApplicationContext val context: Context,
    private val mainRepo: MainRepoImp
) {
    operator suspend fun invoke(userId:Long): List<AlbumsItem?>? {
        var albums: List<AlbumsItem?>?=null
        when (val result = mainRepo.getAlbums(userId)) {
            is Response.Success -> {
                val albumsList = result.data
                albums = albumsList
            }
            is Response.Error -> {
                Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
            }
        }

        return albums
    }
}
