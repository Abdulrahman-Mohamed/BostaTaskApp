package com.abdullrahman.bostataskapp.feature_gallery.presentation.profile.useCases

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.abdullrahman.bostataskapp.feature_gallery.domain.models.User
import com.abdullrahman.bostataskapp.feature_gallery.domain.utils.Response
import com.abdullrahman.bostataskapp.feature_gallery.repository.MainRepoImp
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
/* this class is responsible for get response of Users or error from repo ,
 either fetch a random user and  emit it or display the message */

class GetUserUseCase @Inject constructor(
    @ApplicationContext val context: Context,
    private val mainRepo: MainRepoImp
) {
    operator suspend fun invoke(): User? {
        var user:User?=null
        when (val result = mainRepo.getAllUsers()) {
            is Response.Success -> {
                val userList = result.data
                val rnds = (0 until userList!!.size).shuffled().last()
                user = userList[rnds]
            }
            is Response.Error -> {
                Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
            }
        }

    return user
    }
}