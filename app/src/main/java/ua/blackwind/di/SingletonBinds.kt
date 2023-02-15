package ua.blackwind.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.blackwind.data.cat_facts.CatFactsRemoteDataSource
import ua.blackwind.data.cat_facts.CatFactsRepository
import ua.blackwind.data.cat_facts.ICatFactsRemoteDataSource
import ua.blackwind.data.cat_facts.ICatFactsRepository
import ua.blackwind.data.cat_images.CatImagesRemoteDataSource
import ua.blackwind.data.cat_images.CatImagesRepository
import ua.blackwind.data.cat_images.ICatImagesRemoteDataSource
import ua.blackwind.data.cat_images.ICatImagesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SingletonBinds {
    @Singleton
    @Binds
    abstract fun provideCatFactsRemoteDataSource(dataSource: CatFactsRemoteDataSource): ICatFactsRemoteDataSource

    @Singleton
    @Binds
    abstract fun provideCatImagesRemoteDataSource(dataSource: CatImagesRemoteDataSource): ICatImagesRemoteDataSource

    @Singleton
    @Binds
    abstract fun provideCatFactsRepository(
        repository: CatFactsRepository
    ): ICatFactsRepository

    @Singleton
    @Binds
    abstract fun provideCatImagesRepository(
        repository: CatImagesRepository
    ): ICatImagesRepository
}