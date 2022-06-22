package com.abdullrahman.bostataskapp.di

import android.content.Context
import androidx.room.Room
import com.abdullrahman.bostataskapp.feature_gallery.data.dataSource.api.Api
import com.abdullrahman.bostataskapp.feature_gallery.domain.Constants
import com.abdullrahman.bostataskapp.feature_gallery.presentation.gallery.useCases.GetImagesUseCase
import com.abdullrahman.bostataskapp.feature_gallery.presentation.profile.useCases.GetAlbumsUseCase
import com.abdullrahman.bostataskapp.feature_gallery.presentation.profile.useCases.GetUserUseCase
import com.abdullrahman.bostataskapp.feature_gallery.repository.MainRepoImp
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {
    var gson = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    fun providesOkHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    fun provideApiService(retrofit: Retrofit): Api = retrofit.create(Api::class.java)


    @Provides
    fun provideMainRepo(
        @ApplicationContext appContext: Context,
        api: Api,
    ): MainRepoImp = MainRepoImp(appContext, api)

    @Provides
    @ViewModelScoped
    fun ProvidesUserUseCases(
        @ApplicationContext appContext: Context,
        mainRepo: MainRepoImp
    ): GetUserUseCase = GetUserUseCase(appContext, mainRepo)

    @Provides
    @ViewModelScoped
    fun ProvidesAlbumsUseCases(
        @ApplicationContext appContext: Context,
        mainRepo: MainRepoImp
    ): GetAlbumsUseCase = GetAlbumsUseCase(appContext, mainRepo)
    @Provides
    @ViewModelScoped
    fun ProvidesImagesUseCases(
        @ApplicationContext appContext: Context,
        mainRepo: MainRepoImp
    ): GetImagesUseCase = GetImagesUseCase(appContext, mainRepo)
}