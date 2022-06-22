package com.abdullrahman.bostataskapp.feature_gallery.domain.models

import com.google.gson.annotations.SerializedName

data class ListOfPhotos(

	@field:SerializedName("ListOfPhotos")
	val listOfPhotos: List<ImagesItem?>? = null
)

data class ImagesItem(

	@field:SerializedName("albumId")
	val albumId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("thumbnailUrl")
	val thumbnailUrl: String? = null
)
