package com.abdullrahman.bostataskapp.feature_gallery.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullrahman.bostataskapp.feature_gallery.domain.models.AlbumsItem
import com.abdullrahman.bostataskapp.feature_gallery.domain.models.User
import com.abdullrahman.bostataskapp.feature_gallery.presentation.profile.useCases.GetAlbumsUseCase
import com.abdullrahman.bostataskapp.feature_gallery.presentation.profile.useCases.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userUseCase: GetUserUseCase,
    private val albumsUseCase: GetAlbumsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val userInfoData: MutableLiveData<User> = MutableLiveData<User>()
    val albumsData: MutableLiveData<List<AlbumsItem?>?> = MutableLiveData<List<AlbumsItem?>?>()
    val noConnection = MutableLiveData<Boolean>(false)
    val isLoading = MutableLiveData<Boolean>(true)

    init {
        execute()
    }
    /* execute fetching profile data*/
    fun execute() {
        getUserData()

    }
    /* fetching user data*/
    private fun getUserData() {
        viewModelScope.launch {
            val user = userUseCase()

            if (user != null) {
                userInfoData.value = user
                getAlbumData(userInfoData.value!!.id!!)
                noConnection.value = false

            } else {
                noConnection.value = true
                isLoading.value = false

            }
        }
    }
    /* fetching albums from user_id */
    private fun getAlbumData(userID: Long) {
        viewModelScope.launch {
            albumsData.value = albumsUseCase(userID)
            isLoading.value = false
        }

    }
}