package com.abdullrahman.bostataskapp.feature_gallery.repository

import com.abdullrahman.bostataskapp.feature_gallery.domain.models.AlbumsItem
import com.abdullrahman.bostataskapp.feature_gallery.domain.models.ImagesItem
import com.abdullrahman.bostataskapp.feature_gallery.domain.models.User
import com.abdullrahman.bostataskapp.feature_gallery.domain.utils.Response

interface MainRepo {
    suspend fun getUser(userId:Long): Response<User>
    suspend fun getAllUsers():Response<List<User>>
    suspend fun getAlbums(userId:Long):Response<List<AlbumsItem?>?>
    suspend fun getPhotos(albumId:Long):Response< List<ImagesItem?>?>

}