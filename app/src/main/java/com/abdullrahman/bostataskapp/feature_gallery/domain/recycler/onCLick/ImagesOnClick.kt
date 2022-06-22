package com.abdullrahman.bostataskapp.feature_gallery.domain.recycler.onCLick

import com.abdullrahman.bostataskapp.feature_gallery.domain.models.ImagesItem

interface ImagesOnClick {
    fun onClick(images: ImagesItem)
}