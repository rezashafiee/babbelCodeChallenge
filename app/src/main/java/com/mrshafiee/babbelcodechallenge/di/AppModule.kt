package com.mrshafiee.babbelcodechallenge.di

import com.mrshafiee.babbelcodechallenge.WordUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideWordUtils() = WordUtils()
}