package com.abdullrahman.bostataskapp.feature_gallery.domain.recycler.onCLick

import com.abdullrahman.bostataskapp.feature_gallery.domain.models.AlbumsItem

interface AlbumsOnClick {
    fun onClick(album: AlbumsItem)
}