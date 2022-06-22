package com.abdullrahman.bostataskapp.feature_gallery.presentation.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullrahman.bostataskapp.feature_gallery.domain.models.ImagesItem
import com.abdullrahman.bostataskapp.feature_gallery.presentation.gallery.useCases.GetImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val imagesUseCase: GetImagesUseCase,
    private val savedStateHandle: SavedStateHandle
):ViewModel() {
    val allimagesList = MutableLiveData<List<ImagesItem?>?>()

    var imagesList = MutableLiveData<List<ImagesItem?>?>()
    val isLoading = MutableLiveData<Boolean>(true)
    val noConnection = MutableLiveData<Boolean>(false)
    /* this function is responsible of fetching the images from the use_case and post it to the livedata*/
     fun getImagesList(id:Long){
        viewModelScope.launch {
            isLoading.value = true
            allimagesList.value = imagesUseCase(id)
            SetImagesList()
        }
    }
    /* this function is responsible of checking if the list of images is empty or not ,
     also copy the list to the actual list that would be modified and displayed without change in the original list*/
    private fun SetImagesList()
    {
        if (!allimagesList.value.isNullOrEmpty())
        {
            imagesList.value = allimagesList.value

            noConnection.value = false
        }else{
            noConnection.value = true
        }
        isLoading.value = false
    }
    /* this function is filtering the main list based on the searched title and coby that to the displayed list
    * search_string as a parameter  */
    fun search(search:String?)
    {
        if (search != null && search !=""){
            imagesList.value = allimagesList.value!!.filter { image-> image!!.title!!.contains(search!!)  }
        }else{
            SetImagesList()
        }
    }

}