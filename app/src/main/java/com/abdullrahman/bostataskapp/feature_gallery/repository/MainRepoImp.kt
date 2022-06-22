package com.abdullrahman.bostataskapp.feature_gallery.repository

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import com.abdullrahman.bostataskapp.feature_gallery.data.dataSource.api.Api
import com.abdullrahman.bostataskapp.feature_gallery.domain.models.AlbumsItem
import com.abdullrahman.bostataskapp.feature_gallery.domain.models.ImagesItem
import com.abdullrahman.bostataskapp.feature_gallery.domain.models.User

import com.abdullrahman.bostataskapp.feature_gallery.domain.utils.Response
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MainRepoImp @Inject constructor(
    @ApplicationContext val context: Context,
    val api: Api,
) : MainRepo {
    override suspend fun getUser(userId: Long): Response<User> {
            return try {
                val result = api.getUser(userId)

                if (result.isSuccessful)

                    Response.Success(result.body()!!)
                else
                    Response.Error(result.errorBody()!!.toString())
            }catch (e:HttpException)
            {
                Response.Error(e.localizedMessage?:"un Expected Error")
            }catch (e:IOException)
            {
                Response.Error("Couldn't Reach the Server Check Your Internet Connection ")


            }
    }

    override suspend fun getAllUsers(): Response<List<User>> {
        return try {
            val result =  api.getAllUser()

            if (result.isSuccessful)
                Response.Success(result.body()!!)
            else
                Response.Error(result.errorBody()!!.toString())
        }catch (e:HttpException)
        {
            Response.Error(e.localizedMessage?:"un Expected Error")
        }catch (e:IOException)
        {
            Response.Error("Couldn't Reach the Server Check Your Internet Connection ")


        }

    }

    override suspend fun getAlbums(userId: Long): Response<List<AlbumsItem?>?> {
        return try {
            val result =  api.getAlbums(userId)

            if (result.isSuccessful)

                Response.Success(result.body()!!)
            else
                Response.Error(result.errorBody()!!.toString())
        }catch (e:HttpException)
        {
            Response.Error(e.localizedMessage?:"un Expected Error")
        }catch (e:IOException)
        {
            Response.Error("Couldn't Reach the Server Check Your Internet Connection ")
        }

    }

    override suspend fun getPhotos(albumId: Long): Response<List<ImagesItem?>?> {
        return try {
            val result =  api.getPhotos(albumId)

            if (result.isSuccessful)

                Response.Success(result.body()!!)
            else
                Response.Error(result.errorBody()!!.toString())
        }catch (e:HttpException)
        {
            Response.Error(e.localizedMessage?:"un Expected Error")
        }catch (e:IOException)
        {
            Response.Error("Couldn't Reach the Server Check Your Internet Connection ")
        }

    }



}